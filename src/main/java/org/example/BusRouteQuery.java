package  org.example;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class BusRouteQuery {
    // 假设的API密钥（你需要替换为真实的API密钥）
    private static final String API_KEY = "6188b415301d5c73acbcd111b1bd7554";
    // 假设的API基础URL（你需要替换为实际的API URL）
    private static final String BASE_URL = "http://localhost:81";

    public static void main(String[] args) {
        String originStation = "曹杨路南石路";
        String destinationStation = "仙霞路古北路";

        // 假设的API参数构建方式（根据API文档进行调整）
        String keyParam = "key=" + API_KEY;
        String originParam = "originStation=" + originStation;
        String destinationParam = "destinationStation=" + destinationStation;

        String url = BASE_URL + "?" + originParam + "&" + destinationParam + "&" + keyParam;

        try {
            URL urlObj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();
            System.out.println("Response Code : " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // 假设响应是JSON格式，你需要解析它来获取路线和站点信息
                // 这里只是简单地打印出整个响应
                System.out.println(response.toString());

                // 你应该使用JSON解析库（如Jackson或Gson）来解析JSON并提取所需的信息
            } else {
                System.out.println("Failed to retrieve data");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}