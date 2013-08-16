package yt.bam.minecraftremote;

import java.util.Timer;
import java.util.TimerTask;

import ch.spacebase.mcprotocol.event.DisconnectEvent;
import ch.spacebase.mcprotocol.event.PacketRecieveEvent;
import ch.spacebase.mcprotocol.event.PacketSendEvent;
import ch.spacebase.mcprotocol.event.ProtocolListener;
import ch.spacebase.mcprotocol.exception.ConnectException;
import ch.spacebase.mcprotocol.exception.LoginException;
import ch.spacebase.mcprotocol.exception.OutdatedLibraryException;
import ch.spacebase.mcprotocol.net.Client;
import ch.spacebase.mcprotocol.packet.Packet;
import ch.spacebase.mcprotocol.standard.StandardClient;
import ch.spacebase.mcprotocol.standard.packet.PacketChat;
import ch.spacebase.mcprotocol.standard.packet.PacketPlayerPosition;
import ch.spacebase.mcprotocol.standard.packet.PacketPlayerPositionLook;
import ch.spacebase.mcprotocol.util.Util;

public class Bot {

	public Client client;
	public Listener listener;
	public boolean prepared = false;

	private double x = 0;
	private double y = 0;
	private double z = 0;
	public double vx = 0;
	public double vy = 0;
	public double vz = 0;
	public double stance = 0.1;
	public boolean grounded = false;
	public float pitch;
	public float yaw;

	public Bot(String host, int port) {
		this.client = new StandardClient(host, port);
		this.listener = new Listener();
		this.client.listen(this.listener);
	}

	public void move(double mod){
		PacketPlayerPositionLook packet = new PacketPlayerPositionLook();
		packet.x = x =x+(vx*mod);
		packet.y = y = y+(vy*mod);
		packet.z = z = z+(vz*mod);
		packet.pitch = pitch;
		packet.yaw = yaw;
		packet.stance = 0.1;
		Util.logger().severe(yaw+"-"+pitch);
		packet.grounded = grounded;
		client.send(packet);
	}
	public void setVars(double x,double y,double z){
		vx=x;
		vy=y;
		vz=z;
	}
	public void setYawPitch(float yaw,float pitch){
		this.yaw=yaw;
		this.pitch=pitch;
	}
	public void login(String username, String password) {
		try {
			this.client.login(username, password);
			this.client.connect();
		} catch (ConnectException e) {
			e.printStackTrace();
		} catch (LoginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OutdatedLibraryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void say(String text) {
		PacketChat chat = new PacketChat();
		chat.message = text;
		this.client.send(chat);
	}

	private class Listener extends ProtocolListener {
		@Override
		public void onPacketReceive(PacketRecieveEvent event) {
			Packet packet = event.getPacket();

			switch (event.getPacket().getId()) {
			case 0x0D:
				onPositionLook((PacketPlayerPositionLook) packet);
				break;
			}
		}

		@Override
		public void onPacketSend(PacketSendEvent event) {
		}

		@Override
		public void onDisconnect(DisconnectEvent event) {
			Util.logger().info("Disconnected: " + event.getReason());
		}

		public void onPositionLook(PacketPlayerPositionLook packet) {
			x = packet.x;
			y = packet.y;
			z = packet.z;
			pitch = packet.pitch;
			yaw = packet.yaw;
			stance = packet.stance;
			grounded = packet.grounded;
			prepared = true;
		}
	}

}