import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SSGThread extends Thread {

	private Variety query;
	private String proxyIP;
	public SSGThread(Variety query, String proxyIP) {
		// TODO Auto-generated constructor stub
		this.query = query;
		this.proxyIP = proxyIP;
	}

	@Override
	public void run() {
		int page = 1;
		
		try {
			ArrayList<Variety> lis = new ArrayList<Variety>();
			do {
				String URL = "http://www.ssg.com/search.ssg?target=all&query=" + query.getVariety()
						+ "&page=" + page;

				Connection.Response resp = getResponse(URL);
				if(resp == null || resp.body().length() == 0)
				{
					return;
				}
				
				Document doc = resp.parse();

				if (doc.selectFirst("div[id='area_itemCount']") == null) {
					System.out.println(this.query.getVariety() + " : 조회 결과 없음");
					FileManager.queue.add(this.query.getVariety());
					return;
				}

				int itemCnt = Integer.parseInt(doc.selectFirst("div[id='area_itemCount']").selectFirst("em").text().replaceAll(",",  ""));
				if(itemCnt == 0)
					break;
				int pageCnt = itemCnt / 80;
				int pageRemainCnt = itemCnt % 80;
				int maxPageCnt = pageCnt;
				
				if (pageRemainCnt != 0)
					maxPageCnt = pageCnt + 1;
				
				System.out.println(query.getVariety() + ", " + itemCnt + ", " + maxPageCnt);

				if (resp.statusCode() == 200) {
					Element ullist = doc.select("ul[id='idProductImg']").first(); // selectFirst도있음,
																					// select안에는 태그
																					// 이름
					// select의 리턴 타입은 Element
					Elements liListItemArr = ullist.select("li");

					for (Element liListItem : liListItemArr) {
						Variety variety = new Variety();
						variety.setImg("http:" + liListItem.getElementsByClass("cunit_prod").get(0)
								.selectFirst("img").attr("src"));
						Element info = liListItem.selectFirst("div[class='cunit_info']");
						variety.setName(info.selectFirst("div[class='title']").selectFirst("a")
								.selectFirst("em").text());
						variety.setPrice(info.selectFirst("div[class='opt_price']")
								.selectFirst("em").text().replaceAll(",", ""));
						variety.setUUID(query.getUUID());
						variety.setVariety(query.getVariety());

						// 단위 있는거만
						if (info.selectFirst("div[class='unit']") != null) {
							variety.setUnit(info.selectFirst("div[class='unit']").text());
						}

						String s = variety.getName().replaceAll("\\p{Z}", ""); // 문자열 중간 공백 제거
						
						if (s.contains(query.getVariety())) {
							lis.add(variety);
						}
					}

				} else {
					System.out.println(query.getVariety() + " : 서버 연결 실패");
					//ProxyManager.queue.add(proxyIP);
				}
				
				if (page == maxPageCnt)
					break;
				else
					page++;

				Thread.sleep(4000);
			} while (true);

			System.out.println(query.getVariety() + " - 끝남");
			DBIngredientsManager.getInstance().queue.add(lis);
			
			
			//DBConnection dbc = new DBConnection();
			//dbc.insetIngredient(lis);

		} catch (IOException e) {
			ProxyManager.queue.add(proxyIP);
		} catch (InterruptedException e) {
			
		}
	
		
		
	}

	public Connection.Response getResponse(String URL) {
		
		 String[] proxy = proxyIP.split(":"); //System.out.println(proxyStr);
		 System.setProperty("http.proxyHost", proxy[0]); 
		 System.setProperty("http.proxyPort", proxy[1]);
		
		try {
			
			Connection.Response rp = Jsoup.connect(URL).header("Accept",
					"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3")
					.header("Host", "www.ssg.com")
					.header("Referer",
							"http://www.ssg.com/search.ssg?target=all&query=%EC%9D%BC%E3%85%81%E3%85%87%E3%85%88")
					.header("Upgrade-Insecure-Requests", "1")
					.header("User-Agent",
							"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36")
					.header("Pragma", "no-cache")
					//.header("Cookie",
					//		"_xm_webid_1_=-543998881; PCID=15520262812008641085763; _fbp=fb.1.1552026282430.1275330372; RC_COLOR=24; cto_lwid=272f94f6-abae-4899-8eb6-0d20bfc64977; CHECKED=514accd464dc11e9a895f4034359e7e940794738233417582; FSID=pqcu3r670x4k262lyxk0; CKWHERE=direct_ssg; SSGDOMAIN=www; CSTALK_POPUP_OPEN=null; ssglocale=ko_KR; googtrans=/ko/ko; googtrans=/ko/ko; RC_RESOLUTION=1920*1200; JSESSIONID=AD412EC6E8964BD1A31863CA00B233AB.ssgmall2302; where=SE%3DN%26ET%3D1556249921602%26CHNL_ID%3D0000015208%26CK_WHERE%3Ddirect_ssg%26et%3D1556249921753; FSID1=pqju5c2lyxk671q5o0n8")
					.proxy(proxy[0], Integer.parseInt(proxy[1]))
					.execute();

			return rp;
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println(proxyIP + " : 연결 실패");
			ProxyManager.queue.add(proxyIP);
		}

		return null;
	}


}
