package taskone;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
    private Socket serverSoket;

    public static void main(String[] args) throws IOException {
        Socket server = new Socket("services.tms-studio.ru", 27015);
        BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream()));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(server.getOutputStream()));

        Scanner scanner = new Scanner(System.in);
        String nickName = scanner.nextLine();//код ввода имени с клавиатуры
        System.out.println("Ваш ник: "+ nickName);

        out.write(nickName);
        out.newLine();
        out.flush();

        if(server.isConnected()){
            /* здесь необходимо реализовать создание и запуск потоков
             ReadWorker и WriteWorker */
            Thread readWorker = new Thread(new ReadWorker(server,in));
            Thread writeWorker = new Thread(new WriteWorker(server, out));

            readWorker.start();
            writeWorker.start();

            // цикл, который работает пока подключение к серверу активно
            while(server.isConnected()){
                Thread.onSpinWait();
            }
        }


    }
}
