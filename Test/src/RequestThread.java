import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class RequestThread extends Thread {

	private String query;

	public RequestThread(String query) {
		this.query = query;
	}

	@Override
	public void run() {

		String URL = "http://www.ssg.com/search.ssg?target=all&query=" + query;
		Document doc;
		ArrayList<Variety> varietyList = new ArrayList<Variety>();
		Variety variety = new Variety();
		HashMap<String, Variety> hashMap = new HashMap<String, Variety>();

		try {
			doc = Jsoup.connect(URL).header("Accept",
					"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3")
					.header("Cookie",
							"_xm_webid_1_=6790111; PCID=15538398294988450361987; _fbp=fb.1.1553839826677.1190403840; RC_RESOLUTION=1536*864; RC_COLOR=24; cto_lwid=1828a373-7438-4f78-a6de-8af11161cef9; CHECKED=bff93d6f576011e9927decebb88a022d26882989217448855; floating_ty3=T; FSID=pptv8p5u2p4729hs01x4; CKWHERE=direct_ssg; SSGDOMAIN=www; CSTALK_POPUP_OPEN=null; JSESSIONID=424B33CBDFA54441E1A7009E893E9328.ssgmall4101; where=SE%3DN%26ET%3D1555040272599%26CHNL_ID%3D0000015208%26CK_WHERE%3Ddirect_ssg%26et%3D1555040272762; FSID1=pptwr4hs01x671q5o1z8")
					.header("Host", "www.ssg.com")
					.header("Referer",
							"http://www.ssg.com/search.ssg?target=all&query=%EC%9D%BC%E3%85%81%E3%85%87%E3%85%88")
					.header("Upgrade-Insecure-Requests", "1")
					.header("User-Agent",
							"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36")
					.get(); // document타입으로 받아옴
			if (doc.selectFirst("ul[id='idProductImg']") != null) {
				Element ullist = doc.select("ul[id='idProductImg']").first(); // selectFirst도있음, select안에는 태그 이름
				// select의 리턴 타입은 Element
				Elements liListItemArr = ullist.select("li");
				// Elements중에 하나는 Element
				// int i=0;

				System.out.println("======================================");
				System.out.println(query);
				System.out.println(liListItemArr.size());
				System.out.println("======================================");

				for (Element liListItem : liListItemArr) {
					int count = 0;

					variety.setImg("http:"
							+ liListItem.getElementsByClass("cunit_prod").get(0).selectFirst("img").attr("src"));
					Element info = liListItem.selectFirst("div[class='cunit_info']");
					variety.setName(info.selectFirst("div[class='title']").selectFirst("a").selectFirst("em").text());
					variety.setPrice(info.selectFirst("div[class='opt_price']").selectFirst("em").text());

					if (info.selectFirst("div[class='unit']") != null) {
						variety.setUnit(info.selectFirst("div[class='unit']").text());
					}

					hashMap.put(query, variety);
					System.out.println(hashMap.get(query).getName());
					varietyList.add(count, variety);

					DBConnection db = new DBConnection();
					try {
						//db.connection(varietyList);
					} catch (Exception e) {
						e.printStackTrace();
					}


/*
					  // attr은 태그 속성값 가져오는 것 // 'http:'을 붙여주면 링크가 됨
					  System.out.println("======================================"); //
					  System.out.println("이미지 : " + variety.getImg());
					  System.out.println("이름 : " + variety.getName());
					  System.out.println("가격 : " + variety.getPrice() + "원");
					  System.out.println("단위 당 가격 :" + variety.getUnit());
					  System.out.println("======================================");
*/
				}

			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		super.run();
	}

}
