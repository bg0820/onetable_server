package Main;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import ManagerThread.CrawlerManagerThread;
import ManagerThread.DBIngredientsManager;
import ManagerThread.IgnoreManagerThread;
import Model.AnalyzeVariety;
import Model.HangleAnalyze;

public class SSGThread extends Thread {

	private AnalyzeVariety query;
	private String proxyIP;

	public SSGThread(AnalyzeVariety query,
			String proxyIP) {
		// TODO Auto-generated constructor stub
		this.query = query;
		this.proxyIP = proxyIP;
	}

	@Override
	public void run() {
		int page = 1;
		ArrayList<AnalyzeVariety> lis = new ArrayList<AnalyzeVariety>();
		try {

			// do {
			String URL = "http://www.ssg.com/search.ssg?target=all&query=" + query.getVariety()
					+ "&page=" + page;

			Connection.Response resp = getResponse(URL);
			if (resp == null || resp.body().length() == 0) {

				return;
			}

			Document doc = resp.parse();

			while (true) {
				// itemCount 엘리먼트가 화면상에 생길때까지 대기
				if (doc.selectFirst("input[id='itemCount']") != null) {
					Element inputType = doc.selectFirst("input[id='parmTarget']");

					if (inputType.attr("value").equals("book")) {
						System.out.println(this.query.getVariety() + " : 조회 결과 책");
						IgnoreManagerThread.varietyIgnoreQueue.offer(this.query.getVariety());
						return;
					}

					// value 값이 공백이 아닌경우 탈출
					if (!doc.selectFirst("input[id='itemCount']").attr("value").equals(""))
						break;

					System.out.println(this.query.getVariety() + " - 무한루프 중");
				}

				Thread.sleep(300);
			}

			// 조회 했는데 조회 결과가 0개인 경우 다시 queue 에 넣을 필요 없음
			if (Integer.parseInt(doc.selectFirst("input[id='itemCount']").attr("value")) == 0) {
				System.out.println(this.query.getVariety() + " : 조회 결과 없음");
				IgnoreManagerThread.varietyIgnoreQueue.offer(this.query.getVariety());
				return;
			}

			Element ullist = doc.select("ul[id='idProductImg']").first(); // selectFirst도있음,
																			// select안에는 태그
																			// 이름
			// select의 리턴 타입은 Element
			Elements liListItemArr = ullist.select("li");

			for (Element liListItem : liListItemArr) {
				Element info = liListItem.selectFirst("div[class='cunit_info']");
				String displayName = info.selectFirst("div[class='title']").selectFirst("a")
						.selectFirst("em").text();

				// 형태소 분석해서 객체에 저장
				AnalyzeVariety variety = HangleAnalyze.getInstance().analyze(displayName);
				if (variety != null) {
					// System.out.println(av.getUnitStr());
					variety.setImg("http:" + liListItem.getElementsByClass("cunit_prod").get(0)
							.selectFirst("img").attr("src"));
					variety.setName(displayName);
					variety.setPrice(info.selectFirst("div[class='opt_price']").selectFirst("em")
							.text().replaceAll(",", ""));
					variety.setIdx(query.getIdx());
					variety.setVariety(query.getVariety());


					// 단위 있는거만
					if (info.selectFirst("div[class='unit']") != null) {
						variety.setPerUnit(info.selectFirst("div[class='unit']").text());
					}

					String s = variety.getName().replaceAll("\\p{Z}", ""); // 문자열 중간 공백 제거

					if (s.contains(query.getVariety())) {
						lis.add(variety);
					}
				}
			}

			DBIngredientsManager.getInstance().queue.offer(lis);
			System.out.println(query.getVariety() + " - " + lis.size() + "개 - 성공");
		} catch (Exception e) {
			// IgnoreManagerThread.proxyIgnoreQueue.add(proxyIP);

			CrawlerManagerThread.getInstance().list.push(query);
			System.out.println("[Exception]" + query.getVariety() + " : " + proxyIP + " : 연결 실패 - "
					+ e.getMessage());

		}



	}

	public Connection.Response getResponse(String URL) throws NumberFormatException, IOException {

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
				.header("Pragma", "no-cache").timeout(5000)
				.proxy(proxy[0], Integer.parseInt(proxy[1])).execute();

		return rp;

	}


}
