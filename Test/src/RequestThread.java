import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class RequestThread extends Thread {

	static int success = 0;
	static int failed = 0;
	private IngredientsSubject query;
	static int count = 0;
	// private String proxyIp;

	static ArrayList<Variety> varietyList = new ArrayList<Variety>();
	private String proxyStr;

	public RequestThread(IngredientsSubject query, String proxyStr) {
		this.query = query;
		this.proxyStr = proxyStr;
	}

	@Override
	public void run() {
		ArrayList<Variety> al = new ArrayList<Variety>();

		String URL = "http://www.ssg.com/search.ssg?target=all&query=" + query.getVariety();
		// System.out.println("검색 URL : " + URL);
		Document doc;
		// System.out.println(query.getVariety());
		try {

			// .header("Cookie",
			// "_xm_webid_1_=6790111; PCID=15538398294988450361987;
			// _fbp=fb.1.1553839826677.1190403840; RC_RESOLUTION=1536*864; RC_COLOR=24;
			// cto_lwid=1828a373-7438-4f78-a6de-8af11161cef9;
			// CHECKED=bff93d6f576011e9927decebb88a022d26882989217448855; floating_ty3=T;
			// FSID=pptv8p5u2p4729hs01x4; CKWHERE=direct_ssg; SSGDOMAIN=www;
			// CSTALK_POPUP_OPEN=null;
			// JSESSIONID=424B33CBDFA54441E1A7009E893E9328.ssgmall4101;
			// where=SE%3DN%26ET%3D1555040272599%26CHNL_ID%3D0000015208%26CK_WHERE%3Ddirect_ssg%26et%3D1555040272762;
			// FSID1=pptwr4hs01x671q5o1z8")
			String[] proxy = proxyStr.split(":");
			//System.out.println(proxyStr);
			System.setProperty("http.proxyHost", proxy[0]);
			System.setProperty("http.proxyPort", proxy[1]);
			doc = Jsoup.connect(URL).header("Accept",
					"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3")
					.header("Host", "www.ssg.com")
					.header("Referer",
							"http://www.ssg.com/search.ssg?target=all&query=%EC%9D%BC%E3%85%81%E3%85%87%E3%85%88")
					.header("Upgrade-Insecure-Requests", "1")
					.header("User-Agent",
							"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36")
					.proxy(proxy[0], Integer.parseInt(proxy[1])).get(); // document타입으로 받아옴
			if (doc.selectFirst("ul[id='idProductImg']") != null) {
				Element ullist = doc.select("ul[id='idProductImg']").first(); // selectFirst도있음, select안에는 태그 이름
				// select의 리턴 타입은 Element
				Elements liListItemArr = ullist.select("li");
				// Elements중에 하나는 Element
				// int i=0;

				/*
				 * System.out.println("======================================");
				 * System.out.println(query); System.out.println(liListItemArr.size());
				 * System.out.println("======================================");
				 */
				for (Element liListItem : liListItemArr) {
					Variety variety = new Variety();
					variety.setImg("http:"
							+ liListItem.getElementsByClass("cunit_prod").get(0).selectFirst("img").attr("src"));
					Element info = liListItem.selectFirst("div[class='cunit_info']");
					variety.setName(info.selectFirst("div[class='title']").selectFirst("a").selectFirst("em").text());
					variety.setPrice(
							info.selectFirst("div[class='opt_price']").selectFirst("em").text().replaceAll(",", ""));
					variety.setUUID(query.getUUID());
					variety.setVariety(query.getVariety());

					if (info.selectFirst("div[class='unit']") != null) {
						variety.setUnit(info.selectFirst("div[class='unit']").text());
					}

					String s = variety.getName().replaceAll("\\p{Z}", ""); // 문자열 중간 공백 제거
					//System.out.println(s);
					if (s.contains(query.getVariety())) {
						// 최초 넣는거
						if (!Main.hashMap.containsKey(query.getVariety())) {
							ArrayList<Variety> varietyList = new ArrayList<Variety>();
							varietyList.add(variety);
							Main.hashMap.put(query.getVariety(), varietyList);
							// System.out.println("들어감");
						} else {
							ArrayList<Variety> list = Main.hashMap.get(query.getVariety());
							list.add(variety);
							Main.hashMap.remove(query.getVariety());
							Main.hashMap.put(query.getVariety(), list);
						}

						count++;
					}
				}
				success++;

			} else
				return;

			/*
			for (Variety variety : al) {

				String sql = "INSERT INTO ingredients (IngredientsUUID, IngredientsSubjectUUID, currentPrice, priceDate, displayName, imgURL)"
						+ " VALUES (UNHEX(REPLACE(UUID(), '-', '')), UNHEX(?), ?, CURDATE(), ?, ?)";
				PreparedStatement statement;
				try {
					statement = DBConnectionPool.getInstance().getConnection().prepareStatement(sql);
					statement.setString(1, variety.getUUID());
					statement.setInt(2, Integer.parseInt(variety.getPrice())); // 2,000
					statement.setString(3, variety.getName());
					statement.setString(4, variety.getImg());
					statement.executeUpdate();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


			}*/

		System.out.println("성공 : " + success + ", 실패 : " + failed);
		} catch (IOException e) {
			//e.printStackTrace();
			//System.out.println("[FAILED] 연결 정상적으로 이루어지지 않음 : " + query.getVariety());
			//System.out.println("[FAILED]" + proxy);
			failed++;
		}

		super.run();
	}

}
