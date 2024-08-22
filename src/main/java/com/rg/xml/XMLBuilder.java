package com.rg.xml;

import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;
*/

public class XMLBuilder {
	
	/*
	private List<?> list;

	public XMLBuilder(List<?> list) {
		this.setList(list);

		// String str = resultXMLString(list, "0000");
		// System.out.println(str);
	}
	*/
	/**
	 * Map 객체의 List 배열을 바탕으로 XML을 만든다. 2008.11.11 ddakker 추가
	 * 
	 * @param list
	 *            : XML 의 list 하위 item을 생성할 데이터
	 * @param errCode
	 *            : result 노드에 들어갈 값 | 0: 성공, 기타 에러코드
	 * @return
	 */
	/*
	public String resultXMLString(String id, List<Map<String, String>> columnInfoList) {

		List<?> list = this.list;

		//CDATASection cdata = null;
		Text text = null;
		String xmlString = null;

		try {

			DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
			Document doc = docBuilder.newDocument();

			Element eRoot = doc.createElement("Root");
			doc.appendChild(eRoot);

			eRoot.setAttribute("xmlns", "http://www.nexacroplatform.com/platform/dataset");
			eRoot.setAttribute("ver", "5000");
			
			
			
			Element parameters = doc.createElement("Parameters");
			eRoot.appendChild(parameters);

			Element parameter1 = doc.createElement("Parameter");
			parameters.appendChild(parameter1);

			parameter1.setAttribute("id", "ErrorCode");
			parameter1.setAttribute("type", "int");
			
			text = doc.createTextNode("0");
			parameter1.appendChild(text);


			Element parameter2 = doc.createElement("Parameter");
			parameters.appendChild(parameter2);

			parameter2.setAttribute("id", "ErrorMsg");
			parameter2.setAttribute("type", "string");
			
			text = doc.createTextNode("SUCC");
			parameter2.appendChild(text);


			

			Element dataset = doc.createElement("Dataset");
			eRoot.appendChild(dataset);
			dataset.setAttribute("id", id);


			Element columnInfo = doc.createElement("ColumnInfo");
			dataset.appendChild(columnInfo);
			
			for (int i=0; i<columnInfoList.size(); i++) {
				Element column = doc.createElement("Column");
				columnInfo.appendChild(column);
				
				Map<String, String> map = columnInfoList.get(i);
				column.setAttribute("id", map.get("id"));
				column.setAttribute("type", map.get("type"));
				column.setAttribute("size", map.get("size"));
			}
			
			
			Element eList = doc.createElement("Rows");
			dataset.appendChild(eList);

			int listCnt = list.size();
			//System.out.println("list.size() : " + list.size());
			for (int i = 0; i < listCnt; i++) {
				//System.out.println("i : " + i);
				Element eItem = doc.createElement("Row");
				eList.appendChild(eItem);

				Object o = list.get(i);
				Class<?> c = o.getClass();
				
				System.out.println("c.getCanonicalName() : " + c.getCanonicalName());

				// c.getDeclaredField(name)
				Field[] f = c.getDeclaredFields();

				System.out.println("f.length : " + f.length);

				Map<String, String> map = new HashMap<String, String>();
				for (int j = 0; j < f.length; j++) {
					f[j].setAccessible(true);
					map.put(f[j].getName(), (String) f[j].get(o));
					//System.out.println("key : " + f[j].getName() + ", value : " + (String) f[j].get(o));
				}

				// Map map = (Map)list.get(i);
				Set<String> set = map.keySet();
				Iterator<String> it = set.iterator();
				while (it.hasNext()) {
					String key = (String) it.next();
					Object value = map.get(key);

					Element eKey = doc.createElement("Col");
					eItem.appendChild(eKey);
					eKey.setAttribute("id", key);

					//cdata = doc.createCDATASection(value == null ? "" : value.toString());
					//eKey.appendChild(cdata);
					
					text = doc.createTextNode(value.toString());
					eKey.appendChild(text);
				}
			}

			xmlString = getDOMToString(doc);

		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
			xmlString = "error"; // faultXMLString("1111", "XML생성에 실패 하였습니다.");
		}

		return xmlString;
	}
	*/
	/**
	 * XML 문자열을 DOM(Document) 객체로 변환
	 * 
	 * @param xmlStr
	 *            XML문자열
	 * @return
	 * @throws Exception
	 */
	/*
	private Document getStringToDOM(String xmlStr) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		return builder.parse(new InputSource(new StringReader(xmlStr)));
	}

	/**
	 * DOM(Document) 객체를 XML 문자열로 변환
	 * 
	 * @param xmlStr
	 *            XML문자열
	 * @return
	 * @throws Exception
	 */
	/*
	private String getDOMToString(Document doc) throws Exception {
		TransformerFactory transfac = TransformerFactory.newInstance();
		Transformer trans = transfac.newTransformer();
		trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		trans.setOutputProperty(OutputKeys.INDENT, "yes");

		StringWriter sw = new StringWriter();
		StreamResult result = new StreamResult(sw);
		DOMSource source = new DOMSource(doc);
		trans.transform(source, result);

		return sw.toString();
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	// 출처: http://ddakker.tistory.com/197 [ddakker님의 블로그]
	*/
}
