package com.example.neteasecloudmusic.model

class SongDetailBean {

    /**
     * songs : [{"name":"Tomorrow With You","id":28996630,"pst":0,"t":0,"ar":[{"id":43884,"name":"Senpai","tns":[],"alias":[]},{"id":37825,"name":"Kondor","tns":[],"alias":[]}],"alia":[],"pop":100,"st":0,"rt":null,"fee":8,"v":15,"crbt":null,"cf":"","al":{"id":2943886,"name":"Tomorrow With You","picUrl":"https://p1.music.126.net/asmEcYqB2te2igIlUP_Leg==/6646547789962071.jpg","tns":[],"pic":6646547789962071},"dt":200000,"h":{"br":320000,"fid":0,"size":8020855,"vd":-2},"m":{"br":192000,"fid":0,"size":4812601,"vd":1241},"l":{"br":128000,"fid":0,"size":3208473,"vd":2765},"a":null,"cd":"1","no":1,"rtUrl":null,"ftype":0,"rtUrls":[],"djId":0,"copyright":2,"s_id":0,"mv":0,"rtype":0,"rurl":null,"mst":9,"cp":436013,"publishTime":1394380800000,"tns":["我们的明天"]}]
     * privileges : [{"id":28996630,"fee":8,"payed":3,"st":0,"pl":320000,"dl":320000,"sp":7,"cp":1,"subp":1,"cs":false,"maxbr":320000,"fl":128000,"toast":false,"flag":0,"preSell":false}]
     * code : 200
     */

    var code: Int = 0
    var songs: List<SongsBean>? = null
    var privileges: List<PrivilegesBean>? = null

    class SongsBean {
        /**
         * name : Tomorrow With You
         * id : 28996630
         * pst : 0
         * t : 0
         * ar : [{"id":43884,"name":"Senpai","tns":[],"alias":[]},{"id":37825,"name":"Kondor","tns":[],"alias":[]}]
         * alia : []
         * pop : 100
         * st : 0
         * rt : null
         * fee : 8
         * v : 15
         * crbt : null
         * cf :
         * al : {"id":2943886,"name":"Tomorrow With You","picUrl":"https://p1.music.126.net/asmEcYqB2te2igIlUP_Leg==/6646547789962071.jpg","tns":[],"pic":6646547789962071}
         * dt : 200000
         * h : {"br":320000,"fid":0,"size":8020855,"vd":-2}
         * m : {"br":192000,"fid":0,"size":4812601,"vd":1241}
         * l : {"br":128000,"fid":0,"size":3208473,"vd":2765}
         * a : null
         * cd : 1
         * no : 1
         * rtUrl : null
         * ftype : 0
         * rtUrls : []
         * djId : 0
         * copyright : 2
         * s_id : 0
         * mv : 0
         * rtype : 0
         * rurl : null
         * mst : 9
         * cp : 436013
         * publishTime : 1394380800000
         * tns : ["我们的明天"]
         */

        var name: String? = null
        var id: Int = 0
        var pst: Int = 0
        var t: Int = 0
        var pop: Int = 0
        var st: Int = 0
        var rt: Any? = null
        var fee: Int = 0
        var v: Int = 0
        var crbt: Any? = null
        var cf: String? = null
        var al: AlBean? = null
        var dt: Int = 0
        var h: HBean? = null
        var m: MBean? = null
        var l: LBean? = null
        var a: Any? = null
        var cd: String? = null
        var no: Int = 0
        var rtUrl: Any? = null
        var ftype: Int = 0
        var djId: Int = 0
        var copyright: Int = 0
        var s_id: Int = 0
        var mv: Int = 0
        var rtype: Int = 0
        var rurl: Any? = null
        var mst: Int = 0
        var cp: Int = 0
        var publishTime: Long = 0
        var ar: List<ArBean>? = null
        var alia: List<*>? = null
        var rtUrls: List<*>? = null
        var tns: List<String>? = null

        class AlBean {
            /**
             * id : 2943886
             * name : Tomorrow With You
             * picUrl : https://p1.music.126.net/asmEcYqB2te2igIlUP_Leg==/6646547789962071.jpg
             * tns : []
             * pic : 6646547789962071
             */

            var id: Int = 0
            var name: String? = null
            var picUrl: String? = null
            var pic: Long = 0
            var tns: List<*>? = null
        }

        class HBean {
            /**
             * br : 320000
             * fid : 0
             * size : 8020855
             * vd : -2
             */

            var br: Int = 0
            var fid: Int = 0
            var size: Int = 0
            var vd: Int = 0
        }

        class MBean {
            /**
             * br : 192000
             * fid : 0
             * size : 4812601
             * vd : 1241
             */

            var br: Int = 0
            var fid: Int = 0
            var size: Int = 0
            var vd: Int = 0
        }

        class LBean {
            /**
             * br : 128000
             * fid : 0
             * size : 3208473
             * vd : 2765
             */

            var br: Int = 0
            var fid: Int = 0
            var size: Int = 0
            var vd: Int = 0
        }

        class ArBean {
            /**
             * id : 43884
             * name : Senpai
             * tns : []
             * alias : []
             */

            var id: Int = 0
            var name: String? = null
            var tns: List<*>? = null
            var alias: List<*>? = null
        }
    }

    class PrivilegesBean {
        /**
         * id : 28996630
         * fee : 8
         * payed : 3
         * st : 0
         * pl : 320000
         * dl : 320000
         * sp : 7
         * cp : 1
         * subp : 1
         * cs : false
         * maxbr : 320000
         * fl : 128000
         * toast : false
         * flag : 0
         * preSell : false
         */

        var id: Int = 0
        var fee: Int = 0
        var payed: Int = 0
        var st: Int = 0
        var pl: Int = 0
        var dl: Int = 0
        var sp: Int = 0
        var cp: Int = 0
        var subp: Int = 0
        var isCs: Boolean = false
        var maxbr: Int = 0
        var fl: Int = 0
        var isToast: Boolean = false
        var flag: Int = 0
        var isPreSell: Boolean = false
    }
}
