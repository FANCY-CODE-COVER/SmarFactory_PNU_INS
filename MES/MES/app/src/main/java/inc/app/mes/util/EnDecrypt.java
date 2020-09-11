package inc.app.mes.util;

public class EnDecrypt {
    //vigenere cipher
    private int[] key={3,5,1,9,5,1,7};
    private String user_id;
    private String password;
    private String access_token;
    private String refresh_token;
    public EnDecrypt(){
        user_id="";
        password="";
        access_token="";
        refresh_token="";
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }
    public String makePlainText(){
        return ("======id:"+user_id
                +"======PASSWORD:"+password
                +"======ACCESS_TOKEN:"+access_token
                +"======REFRESH_TOKEN:"+refresh_token+"======||" );
    }

    public String Encrypt(String plaintText){
        String encrypt="";
        for( int i=0; i<plaintText.length();++i){
            encrypt += (char) ((int)plaintText.charAt(i) +(int) key[i%key.length]);
        }
        System.out.println(encrypt);
        return encrypt;
    }
    public String Decrypt(String encryptText){
        String decrypt="";
        for( int i=0; i<encryptText.length();++i){
            decrypt += (char) ((int)encryptText.charAt(i) - (int) key[i%key.length]);
        }
        System.out.println(decrypt);
        return decrypt;
    }
}
