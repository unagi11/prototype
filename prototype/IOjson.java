package prototype;

import java.io.FileNotFoundException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class IOjson {
	public IOjson() {
		try {
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(new FileReader("C:\\json\\myjson.json"));
			JSONObject hello = (JSONObject) obj;
			JSONArray RectArray = (JSONArray) hello.get("Rectangle");
			System.out.println("Rectangles");

			for (int i = 0; i < RectArray.size(); i++) {
				JSONObject RectObject = (JSONObject) RectArray.get(i);

				// JSON name���� ����
				System.out.println("name==>" + RectObject.get("name"));
				System.out.println("location X==>" + RectObject.get("location X"));
				System.out.println("location Y==>" + RectObject.get("location Y"));
				System.out.println("Width==>" + RectObject.get("Width"));
				System.out.println("Height==>" + RectObject.get("Height"));
				// �̷��� �ϸ� ����� �ǰ�......
				// ���� ������ �Ѵ����� �� �������ٰ� get�Ѱ͵��� �����ϸ� �ɲ�����
				// ������ ��Ʈ���� ���͸��� ����
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

class MakeJSON {
	MakeJSON(test.MyComponent mc) {
		JSONObject Rect = new JSONObject();
		JSONArray RectArray = new JSONArray();
		// person�� �Ѹ� ������ �� JSONObject ����
		JSONObject RectInfo = new JSONObject();
		// ���� �Է�
		RectInfo.put("name", mc.name);
		RectInfo.put("location X", mc.p.x);
		RectInfo.put("location Y", mc.p.y);
		RectInfo.put("Width", mc.d.getWidth());
		RectInfo.put("Height", mc.d.getHeight());
		RectArray.add(RectInfo);
		Rect.put("Rectangle", RectArray);
		try {
			FileWriter file = new FileWriter("c:\\json\\myJson.json");
			file.write(Rect.toString());
			file.flush();
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}