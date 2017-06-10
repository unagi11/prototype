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

				// JSON name으로 추출
				System.out.println("name==>" + RectObject.get("name"));
				System.out.println("location X==>" + RectObject.get("location X"));
				System.out.println("location Y==>" + RectObject.get("location Y"));
				System.out.println("Width==>" + RectObject.get("Width"));
				System.out.println("Height==>" + RectObject.get("Height"));
				// 이렇게 하면 출력이 되고......
				// 변수 선언을 한다음에 그 변수에다가 get한것들을 저장하면 될꺼같다
				// 하지만 노트북이 배터리가 없다
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
		// person의 한명 정보가 들어갈 JSONObject 선언
		JSONObject RectInfo = new JSONObject();
		// 정보 입력
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