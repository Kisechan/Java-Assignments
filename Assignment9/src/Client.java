import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Client {

    private static final String SERVER_IP = "127.0.0.1"; // 默认IP
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Socket socket = null;
        DataInputStream dis = null;
        DataOutputStream dos = null;

        try {
            System.out.print("请输入网盘IP地址（默认为 " + SERVER_IP + "）：");
            String serverIpInput = scanner.nextLine();
            String serverIp = serverIpInput.isEmpty() ? SERVER_IP : serverIpInput;

            socket = new Socket(serverIp, SERVER_PORT);
            System.out.println("连接服务端成功！");

            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());

            while (true) {
                System.out.println("\n黑马网盘");
                System.out.println("1. 查看可下载清单");
                System.out.println("2. 上传文件");
                System.out.println("3. 下载文件");
                System.out.print("输入操作编号：");
                String choice = scanner.nextLine();

                switch (choice) {
                    case "1":
                        dos.writeUTF("1");
                        String fileList = dis.readUTF();
                        System.out.println("\n当前可下载的文件：");
                        System.out.println(fileList.isEmpty() ? "暂无文件" : fileList);
                        System.out.println("连接服务端并查看查询可下载清单");
                        break;
                    case "2":
                        System.out.print("请输入需要上传的文件路径（含文件名）：");
                        String uploadFilePath = scanner.nextLine();
                        File fileToUpload = new File(uploadFilePath);
                        if (fileToUpload.isFile()) {
                            dos.writeUTF("2" + fileToUpload.getName());
                            dos.writeLong(fileToUpload.length());
                            try (FileInputStream fis = new FileInputStream(fileToUpload)) {
                                byte[] buffer = new byte[4096];
                                int bytesRead;
                                while ((bytesRead = fis.read(buffer)) != -1) {
                                    dos.write(buffer, 0, bytesRead);
                                }
                                dos.flush();
                                String uploadResult = dis.readUTF();
                                System.out.println(uploadResult);
                            } catch (IOException e) {
                                System.err.println("文件上传失败：" + e.getMessage());
                            }
                        } else {
                            System.out.println("文件路径错误或文件不存在！");
                        }
                        break;
                    case "3":
                        System.out.print("请输入需要下载的文件和下载的目标路径，目标路径不含文件名（输入格式：下载文件名|下载目标路径）：");
                        String downloadRequest = scanner.nextLine();
                        String[] parts = downloadRequest.split("\\|");
                        if (parts.length == 2) {
                            String downloadFileName = parts[0];
                            String downloadPath = parts[1];
                            dos.writeUTF("3" + downloadFileName + "|" + downloadPath);

                            long fileSize = dis.readLong();
                            if (fileSize == -1) {
                                String errorMessage = dis.readUTF();
                                System.out.println("下载失败：" + errorMessage);
                            } else {
                                Path targetPath = Paths.get(downloadPath, downloadFileName);
                                try (FileOutputStream fos = new FileOutputStream(targetPath.toFile())) {
                                    byte[] buffer = new byte[4096];
                                    long totalBytesRead = 0;
                                    int bytesRead;
                                    while (totalBytesRead < fileSize && (bytesRead = dis.read(buffer)) != -1) {
                                        fos.write(buffer, 0, bytesRead);
                                        totalBytesRead += bytesRead;
                                    }
                                    System.out.println("文件 " + downloadFileName + " 下载成功！保存在：" + downloadPath);
                                } catch (IOException e) {
                                    System.err.println("文件下载失败：" + e.getMessage());
                                }
                            }
                        } else {
                            System.out.println("输入格式错误！");
                        }
                        break;
                    default:
                        System.out.println("无效的操作编号，请重新输入。");
                }
            }
        } catch (IOException e) {
            System.err.println("与服务器连接失败：" + e.getMessage());
        } finally {
            try {
                if (dis != null) dis.close();
                if (dos != null) dos.close();
                if (socket != null) socket.close();
                scanner.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}