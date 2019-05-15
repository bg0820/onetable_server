package glit.onetable.model;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import glit.onetable.model.vo.AnalyzeVariety;
import glit.onetable.model.vo.HangleAnalyze;

public class SSGCrawler {
	private final String URL = "http://www.ssg.com/search.ssg?target=all&query=";
	public ArrayList<AnalyzeVariety> crawler(String query, int page) {
		//int page = 1;
		//ArrayList<Ingredient> lis = new ArrayList<Ingredient>();
		//ArrayList<IngredientPrice> pl = new ArrayList<IngredientPrice>();
		ArrayList<AnalyzeVariety> avList = new ArrayList<AnalyzeVariety>();

		try {

			//Spring URL = "http://www.ssg.com/search.ssg?target=all&query=" + query + "&page=" + page;

			Document doc = Jsoup.connect(URL+query+"&page="+page).get();

			if(doc.selectFirst("ul[id='idProductImg']") == null)
				return null;

			Elements liList = doc.selectFirst("ul[id='idProductImg']").select("li");

			for(int i = 0 ; i < liList.size(); i++)
			{
				Element liListItem = liList.get(i);

				String itemId = liListItem.attr("data-adtgtid");
				Element info = liListItem.selectFirst("div[class='cunit_info']");
				String displayName = info.selectFirst("div[class='title']").selectFirst("a")
						.selectFirst("em").text();
				String imgUrl = "http:" + liListItem.getElementsByClass("cunit_prod")
				.get(0).selectFirst("img").attr("src");
				int price = Integer.parseInt(info.selectFirst("div[class='opt_price']")
						.selectFirst("em").text().replaceAll(",", ""));
				//Ingredient ingredient  = new Ingredient();
				//IngredientPrice inPrice = new IngredientPrice();
				AnalyzeVariety av = new AnalyzeVariety();
				av.setName(query);
				av.setImg(imgUrl);
				av.setDisplayName(displayName);
				av.setPrice(price);
				av.setItemId(itemId);
				//ingredient.setIngredientSubject(ingredientSubject);
				//ingredient.setIngredientItemId(itemId);

				AnalyzeVariety analyze = HangleAnalyze.getInstance().analyze(displayName);
				av.setUnitNum(analyze.getUnitNum());
				av.setUnitStr(analyze.getUnitStr());

				avList.add(av);
			}
			return avList;

		} catch (Exception e) {

		}
		return avList;

	}

	public int getRecordCnt(String query) throws IOException {
		//Response resp = getResponse(URL + query);

		//if (resp.statusCode() == 200) {
			Document doc = Jsoup.connect(URL+query).get();

			Element inputType = doc.selectFirst("input[id='parmTarget']");
			if (inputType.attr("value").equals("book")) {
				return 0;
			}

			// 페이지 개수만큼 반복
			return Integer.parseInt(doc.selectFirst("input[id='itemCount']").attr("value"));
		//}

		//return 0;
	}

}
