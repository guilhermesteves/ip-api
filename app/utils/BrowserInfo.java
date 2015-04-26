package utils;

/**
 * May the build success be with you
 * With great problems, comes great help from @guilhermesteves
 *
 * I don't remember why this class is here, but I'm almost
 * certain I'm going to use it as a helper in a future
 * statistics feature
 */
public class BrowserInfo {

    //**********************************************************
    // properties
    //**********************************************************

    private String name;
    private String version;
    private String os;
    private String userAgent;

    //**********************************************************
    // getters and setters
    //**********************************************************

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    private BrowserInfo(){

    }

    //**********************************************************
    // business
    //**********************************************************

    public static BrowserInfo getInstance(String userAgent){

        BrowserInfo binfo = new BrowserInfo();
        BrowserExtractInfo info = null;

        binfo.setUserAgent(userAgent);

        for (BrowserExtractInfo b : BrowserExtractInfo.values()) {
            if (userAgent.contains(b.getIdentify())) {
                info = b;
                break;
            }
        }

        if (info!=null) {
            String version= "n/a";
            String[] split = userAgent.split(info.getVersion());

            if (split.length>1) {
                String[] versionSplit = split[1].split("(\\s|\\.)");
                version = versionSplit.length > 0 ? versionSplit[0] +"."+ versionSplit[1] : version;
            }

            binfo.setName(info.toString());
            binfo.setVersion(version.replaceAll(";",""));

        } else {
            binfo.setName("Other");
            binfo.setVersion("n/a");
        }

        for (OSExtractInfo os : OSExtractInfo.values()) {

            if (userAgent.contains(os.getIdentify())) {
                binfo.setOs(os.toString());
                break;
            }
        }

        return binfo;
    }

    public enum BrowserExtractInfo {

        CHROME ("Chrome"),
        MOZILLA("Gecko", "rv:"),
        FIREFOX ("Firefox"),
        SAFARI ("Apple", "Version/"),
        OPERA ("Opera", "Version/"),
        IE ("MSIE", "MSIE "),

        YANDEX ("Yandex", "Yandex/"),
        OMNIWEB ("OmniWeb", "OmniWeb/"),
        ICAB ("iCab"),
        KONKEROR ("Konqueror"),
        CAMINO ("Camino"),
        NETSCAPE ("Netscape"),
        NETSCAPE_MOZ ("Mozilla");

        private String identify;
        private String version;

        private BrowserExtractInfo(String identifyString, String...versionString){
            this.identify = identifyString;
            this.version = (versionString == null || versionString.length == 0 ? identifyString + "/" : versionString[0]);
        }

        public String getIdentify() {
            return identify;
        }

        public String getVersion() {
            return version;
        }

    }

    public enum OSExtractInfo {
        MAC("Mac"),
        LINUX("Linux"),
        WIN("Windows"),

        IPOD ("iPod"),
        IPHONE ("iPhone"),
        IPAD ("iPad");

        private String identify;

        private OSExtractInfo(String identifyString){
            this.identify = identifyString;
        }

        public String getIdentify() {
            return identify;
        }
    }
}
