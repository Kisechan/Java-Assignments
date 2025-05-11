import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 12345;
    private static final String SERVER_STORAGE_PATH = "server_files";

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();

        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT, 50)) {
            System.out.println("黑马网盘服务器已启动，监听端口：" + SERVER_PORT);

            Path storagePath = Paths.get(SERVER_STORAGE_PATH);
            if (!Files.exists(storagePath)) {
                Files.createDirectories(storagePath);
            }

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("客户端连接成功：" + clientSocket.getInetAddress().getHostAddress());
                executorService.submit(new ClientHandler(clientSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }

    static class ClientHandler implements Runnable {
        private Socket clientSocket;
        private DataInputStream dis;
        private DataOutputStream dos;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
            try {
                this.dis = new DataInputStream(socket.getInputStream());
                this.dos = new DataOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
                closeConnection();
            }
        }

        @Override
        public void run() {
            try {
                while (true) {
                    String command = dis.readUTF();
                    char operation = command.charAt(0);

                    switch (operation) {
                        case '1':
                            sendFileList();
                            break;
                        case '2':
                            uploadFile(command.substring(1));
                            break;
                        case '3':
                            downloadFile(command.substring(1));
                            break;
                        default:
                            dos.writeUTF("未知操作");
                            break;
                    }
                }
            } catch (IOException e) {
                System.out.println("客户端断开连接：" + clientSocket.getInetAddress().getHostAddress());
            } finally {
                closeConnection();
            }
        }

        private void sendFileList() throws IOException {
            File serverDir = new File(SERVER_STORAGE_PATH);
            File[] files = serverDir.listFiles();
            List<String> fileNames = new ArrayList<>();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        fileNames.add(file.getName());
                    }
                }
            }
            dos.writeUTF(String.join("\n", fileNames));
        }

        private void uploadFile(String fileName) throws IOException {
            try {
                long fileSize = dis.readLong();
                Path filePath = Paths.get(SERVER_STORAGE_PATH, fileName);
                try (FileOutputStream fos = new FileOutputStream(filePath.toFile())) {
                    byte[] buffer = new byte[4096];
                    long totalBytesRead = 0;
                    int bytesRead;
                    while (totalBytesRead < fileSize && (bytesRead = dis.read(buffer)) != -1) {
                        fos.write(buffer, 0, bytesRead);
                        totalBytesRead += bytesRead;
                    }
                }
                dos.writeUTF("文件上传成功！");
            } catch (IOException e) {
                dos.writeUTF("文件上传失败：" + e.getMessage());
            }
        }

        private void downloadFile(String request) throws IOException {
            String[] parts = request.split("\\|");
            if (parts.length == 1) {
                dos.writeUTF("下载请求格式错误，请指定文件名");
                return;
            }
            String fileName = parts[0];
            File fileToSend = new File(SERVER_STORAGE_PATH, fileName);

            if (!fileToSend.exists() || !fileToSend.isFile()) {
                dos.writeUTF("文件不存在：" + fileName);
                return;
            }

            dos.writeLong(fileToSend.length()); // 先发送文件大小
            try (FileInputStream fis = new FileInputStream(fileToSend)) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = fis.read(buffer)) != -1) {
                    dos.write(buffer, 0, bytesRead);
                }
                dos.flush();
                System.out.println("文件 " + fileName + " 发送成功至 " + clientSocket.getInetAddress().getHostAddress());
            } catch (IOException e) {
                dos.writeUTF("文件下载失败：" + e.getMessage());
            }
        }

        private void closeConnection() {
            try {
                if (dis != null) dis.close();
                if (dos != null) dos.close();
                if (clientSocket != null) clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}