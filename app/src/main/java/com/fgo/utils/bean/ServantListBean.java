package com.fgo.utils.bean;

import java.io.Serializable;
import java.util.List;

public class ServantListBean implements Serializable {


    /**
     * data : [{"id":1,"name":"玛修・基列莱特","classType":"Shielder"},{"id":2,"name":"阿尔托莉雅・潘德拉贡","classType":"Saber"},{"id":3,"name":"阿尔托莉雅・潘德拉贡(Alter)","classType":"Saber"},{"id":4,"name":"阿尔托莉雅・潘德拉贡(Lily)","classType":"Saber"},{"id":5,"name":"尼禄・克劳狄乌斯","classType":"Saber"},{"id":6,"name":"齐格飞","classType":"Saber"},{"id":7,"name":"盖乌斯・尤利乌斯・恺撒","classType":"Saber"},{"id":8,"name":"阿提拉","classType":"Saber"},{"id":9,"name":"吉尔・德・雷","classType":"Saber"},{"id":10,"name":"骑士迪昂・德・鲍蒙","classType":"Saber"},{"id":11,"name":"卫宫","classType":"Archer"},{"id":12,"name":"吉尔伽美什","classType":"Archer"},{"id":13,"name":"罗宾汉","classType":"Archer"},{"id":14,"name":"阿塔兰忒","classType":"Archer"},{"id":15,"name":"尤瑞艾莉","classType":"Archer"},{"id":16,"name":"阿拉什","classType":"Archer"},{"id":17,"name":"库・丘林","classType":"Lancer"},{"id":18,"name":"伊丽莎白・巴托丽","classType":"Lancer"},{"id":19,"name":"武藏坊弁庆","classType":"Lancer"},{"id":20,"name":"库・丘林(Prototype)","classType":"Lancer"},{"id":21,"name":"列奥尼达一世","classType":"Lancer"},{"id":22,"name":"罗穆路斯","classType":"Lancer"},{"id":23,"name":"美杜莎","classType":"Rider"},{"id":24,"name":"圣乔治","classType":"Rider"},{"id":25,"name":"爱德华・蒂奇","classType":"Rider"},{"id":26,"name":"布狄卡","classType":"Rider"},{"id":27,"name":"牛若丸","classType":"Rider"},{"id":28,"name":"亚历山大","classType":"Rider"},{"id":29,"name":"玛丽・安托瓦内特","classType":"Rider"},{"id":30,"name":"玛尔达","classType":"Rider"},{"id":31,"name":"美狄亚","classType":"Caster"},{"id":32,"name":"吉尔・德・雷","classType":"Caster"},{"id":33,"name":"安徒生","classType":"Caster"},{"id":34,"name":"莎士比亚","classType":"Caster"},{"id":35,"name":"梅菲斯托费勒斯","classType":"Caster"},{"id":36,"name":"莫扎特","classType":"Caster"},{"id":37,"name":"诸葛孔明(埃尔梅罗Ⅱ世)","classType":"Caster"},{"id":38,"name":"库・丘林","classType":"Caster"},{"id":39,"name":"佐佐木小次郎","classType":"Assassin"},{"id":40,"name":"咒腕哈桑","classType":"Assassin"},{"id":41,"name":"斯忒诺","classType":"Assassin"},{"id":42,"name":"荆轲","classType":"Assassin"},{"id":43,"name":"夏尔・亨利・桑松","classType":"Assassin"},{"id":44,"name":"歌剧魅影","classType":"Assassin"},{"id":45,"name":"玛塔・哈丽","classType":"Assassin"},{"id":46,"name":"卡米拉","classType":"Assassin"},{"id":47,"name":"赫拉克勒斯","classType":"Berserker"},{"id":48,"name":"兰斯洛特","classType":"Berserker"},{"id":49,"name":"吕布奉先","classType":"Berserker"},{"id":50,"name":"斯巴达克斯","classType":"Berserker"},{"id":51,"name":"坂田金时","classType":"Berserker"},{"id":52,"name":"弗拉德三世","classType":"Berserker"},{"id":53,"name":"阿斯忒里俄斯","classType":"Berserker"},{"id":54,"name":"卡利古拉","classType":"Berserker"},{"id":55,"name":"大流士三世","classType":"Berserker"},{"id":56,"name":"清姬","classType":"Berserker"},{"id":57,"name":"血斧埃里克","classType":"Berserker"},{"id":58,"name":"玉藻猫","classType":"Berserker"},{"id":59,"name":"贞德","classType":"Ruler"},{"id":60,"name":"俄里翁","classType":"Archer"},{"id":61,"name":"伊丽莎白・巴托里","classType":"Caster"},{"id":62,"name":"玉藻前","classType":"Caster"},{"id":63,"name":"大卫","classType":"Archer"},{"id":64,"name":"赫克托耳","classType":"Lancer"},{"id":65,"name":"弗朗西斯・德雷克","classType":"Rider"},{"id":66,"name":"安妮・伯妮＆瑪莉・瑞德","classType":"Rider"},{"id":67,"name":"美狄亚Lily","classType":"Caster"},{"id":68,"name":"冲田总司","classType":"Saber"},{"id":69,"name":"织田信长","classType":"Archer"},{"id":70,"name":"斯卡哈","classType":"Lancer"},{"id":71,"name":"迪尔姆德・奥迪那","classType":"Lancer"},{"id":72,"name":"弗格斯・马克・罗伊","classType":"Saber"},{"id":73,"name":"阿尔托莉雅・潘德拉贡(圣诞节Alter)","classType":"Rider"},{"id":74,"name":"童谣","classType":"Caster"},{"id":75,"name":"开膛手杰克","classType":"Assassin"},{"id":76,"name":"莫德雷德","classType":"Saber"},{"id":77,"name":"尼古拉・特斯拉","classType":"Archer"},{"id":78,"name":"阿尔托莉雅・潘德拉贡(Alter)","classType":"Lancer"},{"id":79,"name":"冯・霍恩海姆・帕拉塞尔苏斯","classType":"Caster"},{"id":80,"name":"查尔斯・巴贝奇","classType":"Caster"},{"id":81,"name":"亨利・杰基尔＆海德","classType":"Assassin"},{"id":82,"name":"弗兰肯斯坦","classType":"Berserker"},{"id":84,"name":"阿周那","classType":"Archer"},{"id":85,"name":"迦尔纳","classType":"Lancer"},{"id":86,"name":"迷之女主角X","classType":"Assassin"},{"id":87,"name":"芬恩・麦克库尔","classType":"Lancer"},{"id":88,"name":"布伦希尔德","classType":"Lancer"},{"id":89,"name":"贝奥武夫","classType":"Berserker"},{"id":90,"name":"尼禄・克劳狄乌斯(花嫁)","classType":"Saber"},{"id":91,"name":"两仪式","classType":"Saber"},{"id":92,"name":"两仪式","classType":"Assassin"},{"id":93,"name":"天草四郎","classType":"Ruler"},{"id":94,"name":"阿斯托尔福","classType":"Rider"},{"id":95,"name":"幼吉尔","classType":"Archer"},{"id":96,"name":"岩窟王 爱德蒙・唐泰斯","classType":"Avenger"},{"id":97,"name":"南丁格尔","classType":"Berserker"},{"id":98,"name":"库・夫林(Alter)","classType":"Berserker"},{"id":99,"name":"女王梅芙","classType":"Rider"},{"id":100,"name":"海伦娜・布拉瓦茨基","classType":"Caster"},{"id":101,"name":"罗摩","classType":"Saber"},{"id":102,"name":"神枪 李书文","classType":"Lancer"},{"id":103,"name":"托马斯・爱迪生","classType":"Caster"},{"id":104,"name":"杰罗尼莫","classType":"Caster"},{"id":105,"name":"比利小子","classType":"Archer"},{"id":106,"name":"贞德(Alter)","classType":"Avenger"},{"id":107,"name":"安格拉曼纽","classType":"Avenger"},{"id":108,"name":"伊斯坎达尔","classType":"Rider"},{"id":109,"name":"卫宫","classType":"Assassin"},{"id":110,"name":"百貌哈桑","classType":"Assassin"},{"id":111,"name":"爱丽丝菲尔(天之衣)","classType":"Caster"},{"id":112,"name":"酒呑童子","classType":"Assassin"},{"id":113,"name":"玄奘三藏","classType":"Caster"},{"id":114,"name":"源赖光","classType":"Berserker"},{"id":115,"name":"坂田金时","classType":"Rider"},{"id":116,"name":"茨木童子","classType":"Berserker"},{"id":117,"name":"风魔小太郎","classType":"Assassin"},{"id":118,"name":"奥斯曼狄斯","classType":"Rider"},{"id":119,"name":"阿尔托莉雅・潘德拉贡","classType":"Lancer"},{"id":120,"name":"尼托克丽丝","classType":"Caster"},{"id":121,"name":"兰斯洛特","classType":"Saber"},{"id":122,"name":"崔斯坦","classType":"Archer"},{"id":123,"name":"高文","classType":"Saber"},{"id":124,"name":"静谧哈桑","classType":"Assassin"},{"id":125,"name":"表藤太","classType":"Archer"},{"id":126,"name":"贝德维尔","classType":"Saber"},{"id":127,"name":"莱昂纳多・达・芬奇","classType":"Caster"},{"id":128,"name":"玉藻前","classType":"Lancer"},{"id":129,"name":"阿尔托莉雅・潘德拉贡","classType":"Archer"},{"id":130,"name":"玛丽・安托瓦内特","classType":"Caster"},{"id":131,"name":"安妮・伯妮＆玛莉・瑞德","classType":"Archer"},{"id":132,"name":"莫德雷德","classType":"Rider"},{"id":133,"name":"斯卡哈","classType":"Assassin"},{"id":134,"name":"清姬","classType":"Lancer"},{"id":135,"name":"玛尔达","classType":"Ruler"},{"id":136,"name":"伊莉雅丝菲尔・冯・爱因兹贝伦","classType":"Caster"},{"id":137,"name":"克洛伊・冯・爱因兹贝伦","classType":"Archer"},{"id":138,"name":"伊丽莎白・巴托里(Brave)","classType":"Saber"},{"id":139,"name":"克利奥帕特拉","classType":"Assassin"},{"id":140,"name":"弗拉德三世(EXTRA)","classType":"Lancer"},{"id":141,"name":"贞德・Alter・圣诞・Lily","classType":"Lancer"},{"id":142,"name":"伊丝塔","classType":"Archer"},{"id":143,"name":"恩奇都","classType":"Lancer"},{"id":144,"name":"魁札尔・科亚特尔","classType":"Rider"},{"id":145,"name":"吉尔伽美什","classType":"Caster"},{"id":146,"name":"美杜莎","classType":"Lancer"},{"id":147,"name":"戈尔贡","classType":"Avenger"},{"id":148,"name":"豹人","classType":"Lancer"},{"id":150,"name":"梅林","classType":"Caster"},{"id":153,"name":"宫本武藏","classType":"Saber"},{"id":154,"name":"山之翁","classType":"Assassin"},{"id":155,"name":"谜之女主角X(Alter)","classType":"Berserker"},{"id":156,"name":"詹姆斯・莫里亚蒂","classType":"Archer"},{"id":157,"name":"Emiya(Alter)","classType":"Archer"},{"id":158,"name":"海森・罗伯","classType":"Avenger"},{"id":159,"name":"燕青","classType":"Assassin"},{"id":160,"name":"阿瑟・潘德拉刚(Prototype)","classType":"Saber"},{"id":161,"name":"土方岁三","classType":"Berserker"},{"id":162,"name":"茶茶","classType":"Berserker"},{"id":163,"name":"Meltlilith","classType":"Alterego"},{"id":164,"name":"Passionlip","classType":"Alterego"},{"id":165,"name":"铃鹿御前","classType":"Saber"},{"id":166,"name":"BB","classType":"MoonCancer"},{"id":167,"name":"杀生院祈荒","classType":"Alterego"},{"id":169,"name":"雪赫拉莎德","classType":"Caster"},{"id":170,"name":"武则天","classType":"Assassin"},{"id":171,"name":"彭忒西勒亚","classType":"Berserker"},{"id":172,"name":"克里斯多福・哥伦布","classType":"Rider"},{"id":173,"name":"夏洛克・福尔摩斯","classType":"Ruler"},{"id":174,"name":"保罗・班扬","classType":"Berserker"},{"id":175,"name":"尼禄・克劳狄乌斯","classType":"Caster"},{"id":176,"name":"弗兰肯斯坦","classType":"Saber"},{"id":177,"name":"尼托克丽丝","classType":"Assassin"},{"id":178,"name":"织田信长","classType":"Berserker"},{"id":179,"name":"阿尔托莉雅・潘德拉贡(Alter)","classType":"Rider"},{"id":180,"name":"海伦娜・布拉瓦茨基","classType":"Archer"},{"id":181,"name":"源赖光","classType":"Lancer"},{"id":182,"name":"伊修塔尔","classType":"Rider"},{"id":183,"name":"帕尔瓦蒂","classType":"Lancer"},{"id":184,"name":"巴御前","classType":"Archer"},{"id":185,"name":"望月千代女","classType":"Assassin"},{"id":186,"name":"宝蔵院胤舜","classType":"Lancer"},{"id":187,"name":"柳生但马守宗矩","classType":"Saber"},{"id":188,"name":"加藤段蔵","classType":"Assassin"},{"id":189,"name":"刑部姬","classType":"Assassin"},{"id":190,"name":"机械伊丽酱","classType":"Alterego"},{"id":191,"name":"机械伊丽酱II号机","classType":"Alterego"},{"id":192,"name":"喀耳刻","classType":"Caster"},{"id":193,"name":"哪吒","classType":"Lancer"},{"id":194,"name":"示巴女王","classType":"Caster"},{"id":195,"name":"阿比盖尔·威廉姆斯","classType":"Foreigner"},{"id":196,"name":"艾蕾修卡","classType":"Lancer"},{"id":197,"name":"阿提拉·The·Sun(诞)","classType":"Archer"},{"id":198,"name":"葛飾北斎","classType":"Foreigner"},{"id":199,"name":"赛米拉米斯","classType":"Assassin"}]
     * code : success
     * msg : 数据获取成功
     */

    private String code;
    private String msg;
    private List<DataBean> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * id : 1
         * name : 玛修・基列莱特
         * classType : Shielder
         */

        private int id;
        private String name;
        private String classType;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getClassType() {
            return classType;
        }

        public void setClassType(String classType) {
            this.classType = classType;
        }
    }
}
