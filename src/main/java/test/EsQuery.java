package test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.max.InternalMax;
import org.elasticsearch.search.aggregations.metrics.max.Max;
import org.elasticsearch.search.aggregations.metrics.max.MaxAggregationBuilder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

public class EsQuery {

	public static void main(String[] args) throws UnknownHostException {
		//对elasticsearch设置属性，此处只设置了集群名称，如果有对个属性，就继续在后面.put()添加即可
		Settings settings = Settings.builder().put("cluster.name", "wyh-cluster").build();
		TransportClient client = new PreBuiltTransportClient(settings)
										//配置elasticsearch服务ip及端口号。客户端连接的端口号是9300
										.addTransportAddress(new TransportAddress(InetAddress.getByName("192.168.184.128"), 9300));
		System.out.println(client);
		//由于是聚合，这里使用的是AggregationBuilder。maxSecond是自己定义的给查询出来的最大值起的名字，second是elasticsearch中的index里面我们放进去的数据里面的字段名，也就是要在该字段中聚合出最大值
		AggregationBuilder builder = AggregationBuilders.max("maxSecond").field("second");
		//prepareSearch()传入要查询的index
		SearchResponse response = client.prepareSearch("wyh-apache-log").addAggregation(builder).get();
		//从查询结果中获取刚才定义的最大值的名称
		Max max = response.getAggregations().get("maxSecond");
		System.out.println(max.getValue());
		

	}
}

