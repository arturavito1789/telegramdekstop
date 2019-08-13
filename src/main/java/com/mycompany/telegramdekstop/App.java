/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.telegramdekstop;

import com.github.badoualy.telegram.api.Kotlogram;
import com.github.badoualy.telegram.api.TelegramApp;
import com.github.badoualy.telegram.api.TelegramClient;
import com.github.badoualy.telegram.tl.api.TLAbsUser;
import com.github.badoualy.telegram.tl.api.TLUser;
import com.github.badoualy.telegram.tl.api.auth.TLAuthorization;
import com.github.badoualy.telegram.tl.api.auth.TLSentCode;
import com.github.badoualy.telegram.tl.api.contacts.TLContacts;
import com.github.badoualy.telegram.tl.api.upload.TLFile;
import com.github.badoualy.telegram.tl.exception.RpcErrorException;
import org.apache.commons.codec.binary.Base64;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;

/**
 *
 * @author Артур
 */
public class App {
    
     public static void main(String[] args) {
         String phohe_number = "+00000000000";
         TelegramApp application = new TelegramApp(868145, "f060da0313295164b197cea7de9f5e53", "Model",  "SysVer", "1", "en");
         ApiStorage state = new ApiStorage();
         TelegramClient client = Kotlogram.getDefaultClient(application, state);
         try {
              TLSentCode sentCode = client.authSendCode(false, phohe_number, true); 
              System.out.println("Authentication code: ");
              String code = new Scanner(System.in).nextLine();
              TLAuthorization authorization = client.authSignIn(phohe_number, sentCode.getPhoneCodeHash(), code);
              TLUser self = authorization.getUser().getAsUser();
              TLContacts tContacts = (TLContacts) client.contactsGetContacts("");
              Iterator<TLAbsUser> vIt = tContacts.getUsers().iterator();
              while (vIt.hasNext()){
                  TLAbsUser item = vIt.next();
                  TLUser itemU = item.getAsUser();
                  TLFile Photo = client.getUserPhoto(item, true);
                  byte[] b = Photo.getBytes().getData();
                  byte[] encodedBytes = Base64.encodeBase64(b);
                  String src = "data:image/jpeg;base64," + new String(encodedBytes);
                  System.out.println("Путь фото : " + src);
                  System.out.println(itemU.getFirstName() + " " + itemU.getLastName() + " телефон " + itemU.getPhone());
               }
              
              
         } catch (RpcErrorException | IOException e) {
              e.printStackTrace();
         } finally {
              client.close(); 
         }
         
     }
    
}
