package test;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

public class EsTest {

	private static int port = 9300;//通过http请求的端口号是9200，通过客户端请求的端口号是9300
	private static String host = "192.168.184.128";//elasticsearch的服务器地址
	public static void main(String[] args) throws UnknownHostException {
		
		Settings settings = Settings.builder()
									.put("cluster.name", "wyh-es-cluster")//设置es集群名称
									.put("client.transport.sniff", true)//增加嗅探机制，找到es集群
									.build();
		//创建client
		TransportClient client = new PreBuiltTransportClient(settings)
										.addTransportAddress(new TransportAddress(InetAddress.getByName(host), port));
		System.out.println(client);
	}

}
