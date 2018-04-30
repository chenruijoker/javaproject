package wechat;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

class SendTask implements Runnable {
	private int sendPort;

	public SendTask(int sendPort) {
		this.sendPort = sendPort;
	}

	public void run() {
		try {
			DatagramSocket dsa = new DatagramSocket();
			Scanner sdc = new Scanner(System.in);
			while (true) {
				//System.out.println(Thread.currentThread().getName());
				String data = sdc.nextLine();
				byte[] buf = data.getBytes();
				DatagramPacket dps = new DatagramPacket(buf, buf.length, InetAddress.getByName("127.0.0.1"), sendPort);
				//System.out.println(Thread.currentThread().getName());
				dsa.send(dps);
				//Thread.sleep(2000);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}

class ReceiveTask implements Runnable {
	private int receivePort;

	public ReceiveTask(int receivePort) {
		this.receivePort = receivePort;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			DatagramSocket dsaa = new DatagramSocket(receivePort);
			byte[] buff = new byte[1024];
			DatagramPacket dpp = new DatagramPacket(buff, buff.length);
			while (true) {
				/*System.out.print("收到了任务：");*/
				dsaa.receive(dpp);
				/*System.out.print("收到了ddd 任务：");*/
				String str = new String(dpp.getData(), 0, dpp.getLength());
				System.out.println("收到" + dpp.getAddress().getHostAddress() + "---发送的数据---" + str);
				//Thread.sleep(2000);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

}

public class CharRoom {
	public static void main(String[] args) {
		System.out.println("聊天室开始");
		Scanner sc = new Scanner(System.in);
		System.out.println("输入发送端端口号！");
		int sendPort = sc.nextInt();
		System.out.println("输入接收端端口号！");
		int receivePort = sc.nextInt();
		System.out.println("聊天系统开始！");
		new Thread(new SendTask(sendPort), "发送端任务").start();
		new Thread(new ReceiveTask(receivePort), "接收端任务").start();
	}
}
