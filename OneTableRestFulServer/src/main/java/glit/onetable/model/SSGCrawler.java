package glit.onetable.model;

import java.io.IOException;
import java.util.ArrayList;
import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SSGCrawler {
	private final String QUERY_URL = "http://www.ssg.com/search/jsonSearch.ssg?target=item&query="; //http://www.ssg.com/search.ssg?target=all&query=";


	public ArrayList<IngredientModel> getItemList(glit.onetable.model.vo.IngredientSubject ingredientSubject, int page)
			throws IOException {
		ArrayList<IngredientModel> lis = new ArrayList<IngredientModel>();

		Response resp = getResponse(QUERY_URL + ingredientSubject.getVariety() + "&page=" + page);

		if (resp.statusCode() == 200) {
			Document doc = resp.parse();

			if (doc.selectFirst("ul[id='idProductImg']") == null)
				return null;

			Elements liList = doc.selectFirst("ul[id='idProductImg']").select("li");

			for (int i = 0; i < liList.size(); i++) {
				Element liListItem = liList.get(i);

				Element prod = liListItem.selectFirst("div[class='cunit_prod']");
				Element info = liListItem.selectFirst("div[class='cunit_info']");
				
				String itemId = liListItem.attr("data-adtgtid");
				String displayName = prod.selectFirst("input[name='notiTitle']").val();
				String imgUrl = "http:" + prod.selectFirst("input[name='notiImgPath']").val();
				String priceStr =  info.selectFirst("em[class='ssg_price']").text().replaceAll(",", "");
				int price = Integer.parseInt(priceStr);
			
				IngredientModel ingredient = new IngredientModel();
				ingredient.setImgUrl(imgUrl);
				ingredient.setDisplayName(displayName);
				ingredient.setPrice(price);
				ingredient.setIngredientSubject(ingredientSubject);
				ingredient.setIngredientItemId(itemId);
				
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

			// 페이지 개수만큼 반복
			return Integer.parseInt(doc.selectFirst("input[id='target_item_count']").attr("value"));
		}

		return 0;
	}

	private Connection.Response getResponse(String URL) throws NumberFormatException, IOException {



		Connection.Response rp = Jsoup.connect(URL).header("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3")
				.header("Host", "www.ssg.com")
				.header("Referer",
						"http://www.ssg.com/search.ssg?target=all&query=%EC%9D%BC%E3%85%81%E3%85%87%E3%85%88")
				.header("Upgrade-Insecure-Requests", "1")
				.header("User-Agent",
						"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36")
				.header("Pragma", "no-cache").timeout(10000).execute();

		return rp;

	}
}
