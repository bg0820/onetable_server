package glit.onetable.model;

import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import glit.onetable.model.vo.AnalyzeVariety;
import glit.onetable.model.vo.HangleAnalyze;

public class SSGCrawler {
	public ArrayList<AnalyzeVariety> crawler(String query) {
		int page = 1;
		ArrayList<AnalyzeVariety> lis = new ArrayList<AnalyzeVariety>();
		try {

			String URL = "http://www.ssg.com/search.ssg?target=all&query=" + query + "&page=" + page;

			Document doc = Jsoup.connect(URL).get();

			while (true) {
				// itemCount 엘리먼트가 화면상에 생길때까지 대기
				if (doc.selectFirst("input[id='itemCount']") != null) {
					Element inputType = doc.selectFirst("input[id='parmTarget']");

					if (inputType.attr("value").equals("book")) {
						System.out.println(query + " : 조회 결과 책");
						// IgnoreManagerThread.varietyIgnoreQueue.offer(this.query.getVariety());
						return null;
					}

					// value 값이 공백이 아닌경우 탈출
					if (!doc.selectFirst("input[id='itemCount']").attr("value").equals(""))
						break;

					System.out.println(query + " - 무한루프 중");
				}

				Thread.sleep(300);
			}

			// 조회 했는데 조회 결과가 0개인 경우 다시 queue 에 넣을 필요 없음
			if (Integer.parseInt(doc.selectFirst("input[id='itemCount']").attr("value")) == 0) {
				System.out.println(query + " : 조회 결과 없음");
				// IgnoreManagerThread.varietyIgnoreQueue.offer(this.query.getVariety());
				return null;
			}

			Element ullist = doc.select("ul[id='idProductImg']").first(); // selectFirst도있음,
																			// select안에는 태그
																			// 이름
			// select의 리턴 타입은 Element
			Elements liListItemArr = ullist.select("li");

			for (Element liListItem : liListItemArr) {
				Element info = liListItem.selectFirst("div[class='cunit_info']");
				String displayName = info.selectFirst("div[class='title']").selectFirst("a").selectFirst("em").text();

				// 형태소 분석해서 객체에 저장
				AnalyzeVariety variety = HangleAnalyze.getInstance().analyze(displayName);
				if (variety != null) {
					// System.out.println(av.getUnitStr());
					variety.setImg("http:"
							+ liListItem.getElementsByClass("cunit_prod").get(0).selectFirst("img").attr("src"));
					variety.setName(displayName);
					variety.setPrice(
							info.selectFirst("div[class='opt_price']").selectFirst("em").text().replaceAll(",", ""));

					// variety.setIdx(query.getIdx());
					// variety.setVariety(query.getVariety());

					// 단위 있는거만
					if (info.selectFirst("div[class='unit']") != null) {
						variety.setPerUnit(info.selectFirst("div[class='unit']").text());
					}

					String s = variety.getName().replaceAll("\\p{Z}", ""); // 문자열 중간 공백 제거

					if (s.contains(query)) {
						lis.add(variety);
					}

				}
			}

			//return lis;

			// DBIngredientsManager.getInstance().queue.offer(lis);
			// System.out.println(query + " - " + lis.size() + "개 - 성공");
		} catch (Exception e) {
			// IgnoreManagerThread.proxyIgnoreQueue.add(proxyIP);

			// CrawlerManagerThread.getInstance().list.push(query);
			// System.out.println("[Exception]" + query.getVariety() + " : " + proxyIP + " :
			// 연결 실패 - "
			// + e.getMessage());

		}
		return lis;

	}

}
