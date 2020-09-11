import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfChunk;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;

public class HelloWorld {
    public HelloWorld() {
    }

    public static void main(String[] args) {
        try {
            HelloWorld hw = new HelloWorld();

            //生成一个简单PDF文件
//            hw.createHelloWorldPdf();

            //测试分隔符
            hw.splitCharacter();

//            hw.testChange();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void splitCharacter() throws Exception {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("d:\\splitchar.pdf"));
        document.open();
        Chunk chunk = new Chunk("");

        //更改默认分隔符" "和"-"为"/"
        chunk.setSplitCharacter(new SplitCharacter() {
            @Override
            public boolean isSplitCharacter(int start, int current, int end,
                                            char[] cc, PdfChunk[] ck) {
                char c;
                if (ck == null)
                    c = cc[current];
                else
                    c = (char) ck[Math.min(current, ck.length - 1)].getUnicodeEquivalent(cc[current]);

                //用此句替换下面被注释的默认设置
//                if (c == '/')
//                    return true;
                   if (c <= ' ' || c == '-') {
                       return true;
                   }

                if (c < 0x2e80)
                    return false;
                return ((c >= 0x2e80 && c < 0xd7a0)
                        || (c >= 0xf900 && c < 0xfb00)
                        || (c >= 0xfe30 && c < 0xfe50)
                        || (c >= 0xff61 && c < 0xffa0));

            }

        });

        String str = "BEIJING, Sept. 3 (Xinhua) -- Chinese leaders led by Xi Jinping on Thursday morning attended a commemoration in Beijing for the 75th anniversary of the victory of the Chinese People's War of Resistance Against Japanese Aggression and the World Anti-Fascist War.\n" +
                "\n" +
                "The other leaders included Li Keqiang, Li Zhanshu, Wang Yang, Wang Huning, Zhao Leji, Han Zheng and Wang Qishan.\n" +
                "\n" +
                "The event was held at the Museum of the War of Chinese People's Resistance Against Japanese Aggression near Lugou Bridge, also known as Marco Polo Bridge, in the western suburb of Beijing.\n" +
                "\n" +
                "It was also attended by representatives from all walks of life, including veterans who took part in the war, relatives of military officers and martyrs who fought in the war, and relatives of foreign friends who contributed to the victory of the war.\n" +
                "\n" +
                "The commemoration started at 10 a.m. All participants sang the national anthem and then paid a silent tribute to those who sacrificed their lives in the war.\n" +
                "\n" +
                "After the mourning, 14 honor guards laid seven flower baskets, with characters \"Eternal Glory to Martyrs Who Died in Chinese People's War of Resistance Against Japanese Aggression\" on their red ribbons, in the entrance hall of the museum.\n" +
                "\n" +
                "Xi and other leaders ascended the steps and stopped in front of the flower baskets.\n" +
                "\n" +
                "Xi straightened the ribbons on a basket.\n" +
                "\n" +
                "Then, other senior officials and representatives from all walks of life presented bouquets of flowers to martyrs.";


        str = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\"    \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><html xmlns=\"http://www.w3.org/1999/xhtml\">  <head>    <title>      PDF    </title>    <meta http-equiv=\"Content-Type\"    content=\"text/html; charset=UTF-8\" /><style type=\"text/css\" mce_bogus=\"1\">body {    font-size: 12.5px;    line-height: 1.42857143;    color: #333;    background-color: #fff;    font-family: arial,\"Microsoft YaHei\";}ol, ul {    margin-top: 0;    margin-bottom: 10px;    padding: 0;    margin: 0;}img {    vertical-align: middle;    border: 0;}li {list-style-type:none;}.TRS-table {    border: 1px solid #fff;    width: 100%;    border-collapse: collapse;    table-layout:fixed;    word-break: break-all;    word-wrap: break-word;}.TRS-table td {    white-space: normal;    word-spacing: normal;}.TRS-table tbody {    border-bottom: 1px solid #c8c8c8;}.TRS-table td {    padding : 5px 10px;    border: 1px solid #c8c8c8;}.TRS-table thead {    border-bottom: 1px solid #c8c8c8;}.TRS-table thead td {    padding : 10px 20px;    border-bottom: 1px solid #c8c8c8;}.TRS-table tfoot {    border-top: 1px solid #c8c8c8;}.text-right {    text-align: right;}.TRS-tab .panel {    border: none;    border-radius: 0;    box-shadow: none;}.TRS-footer {    background-color: #f7f7f7;    line-height: 50px;}.TRS-tab .panel-body {    padding: 0;}.Dashboard .max-width-wrap {    margin: 0 auto;    max-width: 1141px;}.Rewards-title {    margin-bottom: 10px;    margin-top: 38px;    padding: 0;}.qg strong {    color: #009490;}.Totals-footer {    line-height: 50px;    background-color: #faf1d6;    border-top: 1px solid #c8c8c8;}.Totals-table {    width:100%;}.Totals-title {    line-height: 50px;    border-bottom: 1px solid #c7c8ca;}.Totals_lines {    border-bottom: 1px solid #ccc;}.disclaimer {    clear:both;    /* margin: 10px; */    width: 100%;}.category-name {    padding: 0 10px;}.trs-category-per, .trs-category-amount {    padding: 0 10px;}.trs-dashboard {    line-height: 50px;    color: #fff;    width:100%;    border: 0px solid #c8c8c8;    border-collapse: collapse;    white-space: nowrap;}.trs-total {    position: absolute;    width: 50%;    right: 0;    bottom: -20px;    color: black;    border: 1px solid #c8c8c8;    height: 20px;}div.content {    margin: 0 auto;    width:100%;    max-width: 1000px;}div.content_clientpage {    margin: 0 auto;    width:100%;    max-width: 1000px;}@page {    size: A4 portrait;}@media print{  table { page-break-after:auto; }  tr    { page-break-inside:avoid; page-break-after:auto }  td    { page-break-inside:avoid; page-break-after:auto }  thead { display:table-header-group }  tfoot { display:table-footer-group }}.row{    margin-right: 0px;    margin-left: 0px;}.JLT-PBxslider {    height: 100%;}.PBxslider {    margin-left: 0px;    margin-right: 0px;}.titleImg{    display: inline-block;    float: none;    height: 30px;    width:30px;    margin-top: -6px;    margin-bottom:0px;    vertical-align: middle}.max-width-wrap {    max-width: 1141px;    margin: 0 auto;}.bx-viewport{    width:100% !important;    overflow:hidden !important;    position:relative !important;    height:100% !important;}#client-page-content img {    width: 100% !important;    height: auto !important;}#allAnnouncement img{    width: 100% !important;    height: auto !important;}.dashboard-box{    background-color: #f7f7f7;    position: relative;}.f-cb:after, .f-cbli li:after {  display: block;  clear: both;  visibility: hidden;  height: 0;  overflow: hidden;  content: \".\";}.f-cb, .f-cbli li {  zoom: 1}</style><style type=\"text/css\">.img-s{height:auto;}@media only screen and (max-width: 767px) {.img-s{height:450px;}}</style>  </head>  <body>    <div class=\"content\">      <h1>        Client1      </h1>      <!-- 涓?\uD8AD\uDCDE骞垮\uD8A8\uDCE1?\uD8A6\uDC20-->      <div id=\"allAnnouncement\"      class=\"col-xs-12 col-sm-12 col-md-12 col-lg-12 Rewards-home-wrapper\"       style=\"margin-top:0;\">        <div class=\"Rewards-wrapper row\">          <div>            <img            src=\"http://10.177.0.37/shop/upload/common/81a160a7-d55c-4881-8b16-28504aa29d11.jpg\"             style=\"width:98.7%;\" alt=\"\" />           </div>        </div>      </div>      <p>        Hi锛\uD8A3\uDC4Dillie20<br />        娆㈣?      </p>      <p>        aaa      </p>      <div class=\"disclaimer\">        <div style=\"font-weight: bold;\">          <p>            据中国军网，中国人民解放军西部战区新闻发言人张水利大校就印军再次非法越线挑衅发表谈话指出，9月7日，印军非法越线进入中印边境西段班公湖南岸神炮山地域。印军在行动中悍然对前出交涉的中国边防部队巡逻人员鸣枪威胁，中国边防部队被迫采取应对措施稳控现地局势。印方行径严重违反中印双方有关协议协定，推高地区紧张局势，极易造成误解误判，是严重的军事挑衅行为，性质非常恶劣。我们要求印方立即停止危险行动，立即撤回越线人员，严格约束一线部队，严肃查处鸣枪挑衅人员，确保不再发生类似事件。战区部队将坚决履行职责使命，坚决捍卫国家领土主权。     </p>          <p>            <strong>濡\uD899\uDC3F\uD8B5\uDCF3?ㄦ\uD8B3\uDCE0浠讳??\uD8A8\uDC3F\uD8AE\uDC00锛\uD8A3\uDC3F??\uD8AB\uDC3F郴?稿\uD89C\uDC3F浜哄\uD8A1\uDCF2璧\uD89B\uDC3F?涓\uD8B1\uDC3F\uD8A1\uDC3F浼\uD8B0\uDC3F即?\uD899\uDC3C/strong>&nbsp;1          </p>        </div>      </div>    </div>    <!-- client page1椤甸\uD8B4\uDC3F -->    <div style=\"page-break-before: always;\">      <h1>        姒\uD899\uDC3F堪1      </h1>      <div class=\"row max-width-wrap\">        <div id=\"client-page-content\"        class=\"col-xs-12 col-sm-12 col-md-12 col-lg-12 Rewards-home-wrapper\"         style=\"margin:30px 0;\">          <p>            fdaf          </p>        </div>      </div>    </div>    <div class=\"content\" style=\"page-break-before: always;\">      <table frame=\"void\">        <tr style=\"vertical-align: middle;\">          <td>            <img            src=\"http://10.177.0.37/shop/resources/vendor/images/trs/trs_sanofi_xinchou.png\"             class=\"titleImg\" />           </td>          <td style=\"width: 500px;\">            <h1>              ??\uD89C\uDC00            </h1>          </td>        </tr>      </table>      <p>      </p>      <p>       据中国军网，中国人民解放军西部战区新闻发言人张水利大校就印军再次非法越线挑衅发表谈话指出，9月7日，印军非法越线进入中印边境西段班公湖南岸神炮山地域。印军在行动中悍然对前出交涉的中国边防部队巡逻人员鸣枪威胁，中国边防部队被迫采取应对措施稳控现地局势。印方行径严重违反中印双方有关协议协定，推高地区紧张局势，极易造成误解误判，是严重的军事挑衅行为，性质非常恶劣。我们要求印方立即停止危险行动，立即撤回越线人员，严格约束一线部队，严肃查处鸣枪挑衅人员，确保不再发生类似事件。战区部队将坚决履行职责使命，坚决捍卫国家领土主权。    </p>      <br />      <br />      </div>    <div class=\"content\" style=\"page-break-before: always;\">      <table frame=\"void\">        <tr>          <td>            <img            src=\"http://10.177.0.37/shop/resources/vendor/images/trs/trs_sanofi_buchongfuli.png\"             class=\"titleImg\" />          </td>          <td style=\"width: 500px;\">            <h1>              琛ュ\uD89C\uDCDC绂\uD8A6\uDC3F\uD89F\uDC3F            </h1>          </td>        </tr>      </table>      <p>        据中国军网，中国人民解放军西部战区新闻发言人张水利大校就印军再次非法越线挑衅发表谈话指出，9月7日，印军非法越线进入中印边境西段班公湖南岸神炮山地域。印军在行动中悍然对前出交涉的中国边防部队巡逻人员鸣枪威胁，中国边防部队被迫采取应对措施稳控现地局势。印方行径严重违反中印双方有关协议协定，推高地区紧张局势，极易造成误解误判，是严重的军事挑衅行为，性质非常恶劣。我们要求印方立即停止危险行动，立即撤回越线人员，严格约束一线部队，严肃查处鸣枪挑衅人员，确保不再发生类似事件。战区部队将坚决履行职责使命，坚决捍卫国家领土主权。 </p>      <!-- ?\uD8A0\uDC63lient page2 ?\uD8B0\uDC3F??剧ず浜\uD8A3\uDC3F骇?\uD8B3\uDC3F\uD8A4\uDCEC?\uD89B\uDC3F０?\uD8A5\uDC3F\uD89A\uDC3F?\uD89D\uDC3F?娌℃\uD8B3\uDCE0灏辨\uD8AF\uDC3F绀哄\uD8B3\uDC3F?\u0080?\uD8A5\uDC3F?椤?-->    </div>    <div class=\"content\" style=\"page-break-before: always;\">      <table frame=\"void\">        <tr>          <td class=\"Rewards-title \">            <img            src=\"http://10.177.0.37/shop/resources/vendor/images/trs/trs_sanofi_quanmianxinchou.png\"             class=\"titleImg\" />           </td>          <td style=\"width: 500px;\">            <h1>              ?ㄩ\uD8B4\uDC3F??\uD89C\uDC00            </h1>          </td>        </tr>      </table>      <p>      </p>      <!-- ?\uD8A0\uDC63lient page2 ?\uD8B0\uDC3F??剧ず浜\uD8A3\uDC3F骇?\uD8B3\uDC3F\uD8A4\uDCEC?\uD89B\uDC3F０?\uD8A5\uDC3F\uD89A\uDC3F?\uD89D\uDC3F?娌℃\uD8B3\uDCE0灏辨\uD8AF\uDC3F绀哄\uD8B3\uDC3F?\u0080?\uD8A5\uDC3F?椤?-->    </div>    <!-- client page2椤甸\uD8B4\uDC3F -->    <div class=\"content_clientpage\"    style=\"page-break-before: always;\">      <h1>        ?\uD8A3\uDC3F??\uD8A8\uDC3F?1      </h1>      <div class=\"row max-width-wrap\">        <div id=\"client-page-content\"        class=\"col-xs-12 col-sm-12 col-md-12 col-lg-12 Rewards-home-wrapper\"         style=\"margin:30px 0;\">          <p>          </p>          <h1 class=\"Rewards-title \">            <img alt=\"\" class=\"titleImg\"            src=\"http://10.177.0.37/shop/upload/common/4f4107cf-2535-4397-a807-40fe09bf2a8e.png\"             style=\"width: 35px; height: 35px;\" />?\uD8A3\uDC3F??\uD8A8\uDC3F?          </h1>          璧\uD8B2\uDC3F??查\uD8B2\uDCDD?㈠\uD8B4\uDCF1淇′互?\uD89F\uDC3F?涓烘\uD8B7\uDC3F蹇\uD89A\uDC3F?浠ョ哗?\uD89F\uDC3F负瀵煎\uD8A7\uDCE8??┍?ㄥ\uD89C\uDC00?镐??℃\uD89F\uDCE7?\uD8B6\uDC3F\uD8B1\uDCDB?\uD8A4\uDC3F??\uD8B7\uDC3F?锛\uD8A3\uDC3F?楂\uD8AF\uDC3F哗?\uD89F\uDC3F\uD8A8\uDCEF宸ョ\uD8B1\uDCDB璁ゅ\uD8A6\uDC00?\uD8A3\uDC3F??变???哗?\uD89F\uDC3F\uD8AD\uDCDE?\uD8AD\uDC3F\uD8B1\uDCDB?抽\uD8AB\uDC00?\u0080???灏\uD89D\uDC3F?????辨\uD89F\uDCE8浠?\uD8B1\uDCDB浼\uD8AF\uDC3F??\uD8AF\uDC3F伐涓\uD8A5\uDC3F?璇鸿\uD8A6\uDC3F?卞\uD8A7\uDCE3?\uD8A7\uDC3F\uD8AC\uDC3F锛\uD898\uDC3Cbr />          ?\uD8B6\uDC3F阿?ㄤ负璧\uD8B2\uDC3F??插\uD898\uDCF1?虹\uD8B1\uDCDB?\uD898\uDC3F画璐＄\uD8A3\uDC00锛\uD8A3\uDC3F\uD8A0\uDC3F浜\uD8A5\uDC3F\uD899\uDC3F?\uD89B\uDC3F伐浣\uD8B3\uDC3F〃?帮??\uD8A8\uDC3F滑?ｅ垢?伴\u0080\uD8B1\uDC3F\uD8B6\uDC3F????2019骞??\uD89F\uDC31?ヨ捣锛\uD8A3\uDC3F\uD899\uDC3F?\uD89B\uDC3F??ㄥご琛\uD8AB\uDC3F?琚?换?戒负?\uD899\uDC3F\uD89E\uDC002018骞磋捣璧\uD8B2\uDC3F??蹭腑?藉\uD89F\uDCF2寤轰?涓\u0080濂\uD8AE\uDC3F\uD89D\uDCDC?ㄥご琛\uD8AB\uDC3F??\uD89D\uDC3F?绯伙???互?\uD8AA\uDC3F??\uD8AF\uDC3F伐?ㄥ\uD89C\uDC00?搁\uD8B2\uDCDD?㈠\uD89D\uDCDC?\uD89B\uDC3F\uD898\uDCE3涓\uD8B1\uDC3F\uD8A6\uDCE8灞\uD8AC\uDC3F\u0080\uD8AB\uDC3F??\uD899\uDC3F\uD899\uDC3F??互?\uD8A8\uDC3F\uD899\uDC3F?\uD89B\uDC3F富绠℃\uD89F\uDCED浜哄\uD8A1\uDCF2璧\uD89B\uDC3F?浼\uD8B0\uDC3F即寰\uD898\uDC3F???繁?\uD89B\uDC3F\uD89D\uDCDC?ㄥご琛\uD8AB\uDC3F俊??\u0080\uD899\uDC3Cbr />          ?\uD8A8\uDC3F滑璋ㄥ\u0080\uD8B6\uDC3F?娆℃\uD8B3\uDC3F浼\uD8B1\uDC3F??ㄧ\uD8B1\uDCDB?板\uD89E\uDC3F璐＄\uD8A3\uDC00琛ㄧず?\uD8B6\uDC3F阿锛\uD8A3\uDC3F\uD8B3\uDCF6寰\uD89C\uDC3F\uD899\uDC3F?ㄤ??\uD8A5\uDC3F\uD8B1\uDCDB宸ヤ?涓?\uD89D\uDCE4?ュ\uD89D\uDCE4?\uD8A0\uDC3F?涔\uD8B6\uDC3F??挎\uD899\uDC3F?藉\uD8A6\uDCED寰\uD8AE\uDC3F\uD8B2\uDC3F澶х\uD8B1\uDCDB?\uD8A7\uDC3F\uD8A1\uDCF6锛\uD898\uDC20<img          alt=\"\"          src=\"http://10.177.0.37/shop/upload/common/586a0b60-fe16-41c4-9b5e-875e09d1dcb9.jpg\"           style=\"width: 1584px; height: 894px;\" /><br />          <b><i>?\uD8A2\uDC3F\uD8A4\uDCDE椤甸\uD8B4\uDC3F 澹版\uD8AF\uDCE5?\uD89C\uDC3F??\u0080瑕\uD898\uDC3F\uD8A7\uDCE5?拌\uD89E\uDC00瀹\uD8B1\uDC3F??\uD8A4\uDC3F疆锛\uD8A3\uDC3F\uD8B7\uDC3F?wording?\uD8A4\uDC3F疆?剧ず</i></b> <br />          <br />        </div>      </div>    </div>  </body></html>\n";
        Paragraph paragraph1 = new Paragraph(chunk);
        chunk.append(str);
        paragraph1.add(chunk);
        document.add(paragraph1);
        document.close();
    }

    public void testChange() throws Exception {
        Document doc = new Document();
        PdfWriter.getInstance(doc, new FileOutputStream("d:\\testChange.pdf"));
        doc.open();
        Chunk chunk = new Chunk("a");
        doc.add(new Paragraph("before change: " + chunk));

        //在chunk对象更改之前已经定义好了paragraph对象
        Paragraph p = new Paragraph(chunk);

        chunk.append("bc");
        doc.add(new Chunk("after change: " + chunk));
        doc.add(p);
        doc.close();
    }

}
