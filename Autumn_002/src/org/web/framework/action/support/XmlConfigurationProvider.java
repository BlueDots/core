package org.web.framework.action.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.web.exception.AutumnException;
import org.web.framework.ConfigLibrary;
import org.web.framework.action.ConfigurationProvider;
import org.web.framework.action.config.ActionConfig;
import org.web.framework.action.config.Config;
import org.web.framework.action.config.ConstantConfig;
import org.web.framework.action.config.ResultConfig;
import org.web.servlet.Action;
import org.web.servlet.support.RebuildActionDocument;
import org.web.util.RegUtil;

import tool.mastery.core.ConfigurationUtil;
import tool.mastery.core.StringUtil;

public class XmlConfigurationProvider implements ConfigurationProvider {

	private static final Map<String, Config> map = new HashMap<String, Config>();

	private String configFileName;

	private Set<String> KEYS;

	public XmlConfigurationProvider(String filename) {
		this.configFileName = filename;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void register() {
		try {
			Element rootElement = ConfigurationUtil.configByXml(
					new RebuildActionDocument(), configFileName);
			List list = rootElement.elements();
			
			for (int i = 0; i < list.size(); i++) {
				Element element = (Element) list.get(i);
				if(element.getName().equalsIgnoreCase("constant")) {
					String name= element.attributeValue("name");
					String value= element.attributeValue("value");
					if(StringUtil.StringIsNull(name) && !StringUtil.StringIsNull(value)) {
						throw new AutumnException("<constant>配置中value属性有值，但name属性尚未配置。");
					}
					ConstantConfig.getInstance().put(name, value);
					continue;
				}
				Config config = new Config();
				String name = element.attributeValue("name");
				String clazz = element.attributeValue("class");
				String match = element.attributeValue("match");
				String validateToken = element.attributeValue("validate_token");
				ActionConfig actionConfig = new ActionConfig(
						RegUtil.processReg(name), clazz);
				if (!StringUtil.StringIsNull(match)) {
					actionConfig.setMatch(Integer.parseInt(RegUtil
							.deleteBraces(match)));
				}
				if(!StringUtil.StringIsNull(validateToken)) {
					actionConfig.setValidate_token(Boolean.parseBoolean(validateToken));
				}
				config.setActionConfig(actionConfig);
				List<Element> currentResults = element.elements("result");
				List<ResultConfig> results = new ArrayList<ResultConfig>();
				for (Element result : currentResults) {
					String resultName = result.attributeValue("name");
					if (StringUtil.StringIsNull(resultName)) {
						resultName = Action.SUCCESS;
					}
					String type = result.attributeValue("type");
					if (StringUtil.StringIsNull(type)) {
						type = ConfigLibrary.DEFAULT_RESULT_TYPE;
					}
					String dispatcher = result.getText();
					ResultConfig resultConfig = new ResultConfig(resultName,
							type, dispatcher);
					results.add(resultConfig);
				}
				config.setResultConfig(results);
				map.put(actionConfig.getName(), config);
			}
		} catch (DocumentException e) {
			new AutumnException(e);
		}
		this.KEYS = map.keySet();
	}

	@Override
	public Config getConfig(String name) {
		for (Iterator<String> it = KEYS.iterator(); it.hasNext();) {
			String patternKey = it.next();
			if (name.matches(patternKey)) {
				return map.get(patternKey);
			}
		}
		return null;
	}
}
