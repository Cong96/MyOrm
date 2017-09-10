package wangcc.com.MyOrm;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.wangcc.config.Config;
import com.wangcc.config.ConfigBuilder;
import com.wangcc.mapper.MappedBeanResult;
import com.wangcc.session.ResultMap;
import com.wangcc.session.SessionFactory;

public class TestXml {
	public static void main(String[] args) throws IOException {
		SessionFactory sessionFactory = ConfigBuilder.build("config.xml");
		Config config = sessionFactory.getConfig();
		System.out.println(config.toString());
		Map<String, ResultMap> resultMap = config.getReslutMap();
		Set<Entry<String, ResultMap>> set = resultMap.entrySet();
		for (Entry<String, ResultMap> entry : set) {
			System.out.println("KEY:" + entry.getKey());
			System.out.println("VALUE:" + entry.getValue().toString());
			MappedBeanResult result = (MappedBeanResult) entry.getValue();
			Map<String, Field> propsMap = result.getPropsMap();
			for (Entry<String, Field> entry1 : propsMap.entrySet()) {
				System.out.println("PROPKEY:" + entry1.getKey());
				System.out.println("PROPVALUE:" + entry1.getValue());
			}

		}
	}
}
