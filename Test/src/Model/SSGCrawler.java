package Model;

import java.io.IOException;
import java.util.ArrayList;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SSGCrawler {
	private final String QUERY_URL = "http://www.ssg.com/search.ssg?target=all&query=";
	private String proxyIP;

	public SSGCrawler(String proxyIP) {
		this.proxyIP = proxyIP;
	}

	public ArrayList<Ingredient> getItemList(IngredientSubject ingredientSubject, int page)
			throws IOException {
		ArrayList<Ingredient> lis = new ArrayList<Ingredient>();

		Response resp = getResponse(QUERY_URL + ingredientSubject.getVariety() + "&page=" + page);

		if (resp.statusCode() == 200) {
			Document doc = resp.parse();

			if (doc.selectFirst("ul[id='idProductImg']") == null)
				return null;

			Elements liList = doc.selectFirst("ul[id='idProductImg']").select("li");

			for (int i = 0; i < liList.size(); i++) {
				Element liListItem = liList.get(i);

				String itemId = liListItem.attr("data-adtgtid");
				Element info = liListItem.selectFirst("div[class='cunit_info']");
				String displayName = info.selectFirst("div[class='title']").selectFirst("a")
						.selectFirst("em").text();
				String imgUrl = "http:" + liListItem.getElementsByClass("cunit_prod").get(0)
						.selectFirst("img").attr("src");
				int price = Integer.parseInt(info.selectFirst("div[class='opt_price']")
						.selectFirst("em").text().replaceAll(",", ""));
				Ingredient ingredient = new Ingredient();
				ingredient.setImgUrl(imgUrl);
				ingredient.setDisplayName(displayName);
				ingredient.setPrice(price);
				ingredient.setIngredientSubject(ingredientSubject);
				ingredient.setIngredientItemId(itemId);

				// 형태소 분석해서 객체에 저장
				/*
				 * Ingredient variety = HangleAnalyze.getInstance().analyze(displayName); if
				 * (variety != null) {
				 * 
				 * // System.out.println(av.getUnitStr()); variety.setImgUrl();
				 * variety.setDisplayName(displayName); variety.setPrice();
				 * variety.setIngredientSubject(ingredientSubject);
				 * variety.setIngredientItemId(itemId); }
				 */
				if (itemId != null) {
					if (!itemId.equals("")) {
						lis.add(ingredient);
					}
				}
			}
			return lis;
		} else {
			return null;
		}
	}

	public int getRecordCnt(String query) throws IOException {
		Response resp = getResponse(QUERY_URL + query);

		if (resp.statusCode() == 200) {
			Document doc = resp.parse();

			Element inputType = doc.selectFirst("input[id='parmTarget']");
			if (inputType.attr("value").equals("book")) {
				return 0;
			}

			// 페이지 개수만큼 반복
			return Integer.parseInt(doc.selectFirst("input[id='itemCount']").attr("value"));
		}

		return 0;
	}

	private Connection.Response getResponse(String URL) throws NumberFormatException, IOException {

		String[] proxy = proxyIP.split(":"); // System.out.println(proxyStr);
		// System.setProperty("http.proxyHost", proxy[0]);
		// System.setProperty("http.proxyPort", proxy[1]);


		Connection.Response rp = Jsoup.connect(URL).header("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3")
				.header("Host", "www.ssg.com")
				.header("Referer",
						"http://www.ssg.com/search.ssg?target=all&query=%EC%9D%BC%E3%85%81%E3%85%87%E3%85%88")
				.header("Upgrade-Insecure-Requests", "1")
				.header("User-Agent",
						"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36")
				.header("Pragma", "no-cache").timeout(10000)
				.proxy(proxy[0], Integer.parseInt(proxy[1])).execute();

		return rp;

	}
}
