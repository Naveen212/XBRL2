/*
 * Class Name:    XbrlApi
 *
 * Author:        Your Name
 * Creation Date: Sunday, March 25 2018, 13:55 
 * Last Modified: Sunday, March 25 2018, 14:26
 * 
 * Class Description:
 *
 */
import java.util.*;
import java.io.*;
import java.net.*;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import org.xml.sax.InputSource;

public class XbrlApi
  
{ 
  // These are template Url's.We'll be concatenating them with the user input.
  final static String myUrlTemp="https://csuite.xbrl.us/php/dispatch.php?Task=xbrlCIKLookup&%20Ticker=";
  final static String myUrlTemp2="https://csuite.xbrl.us/php/dispatch.php?Task=xbrlValues&Element=Assets&Year=2014&Period=Y&API_Key=4b979bba-7f27-4643-9d52-6b2cdb5a924a&CIK=";
public static void main(String[] args) throws Exception
{
// Get the user to Input the unique Ticker of the company to obtain it's CIK. WIthout CIK the data will not be extracted.
Scanner kb=new Scanner(System.in);
System.out.println("Enter the Ticker ");
String ticker=kb.nextLine();

  //call to CIK function which requires the user inputed ticker and returns the CIK coresponding to that.
String cik=CIK(ticker); 
System.out.println("Cik is: "+cik);

  // call to XBValues function that takes the CIK and returns the Element we need. In this case the Elemnt is hard coded to Assets

String AssetsAmount=XBValues(cik); 
System.out.println("Assests Amount is: "+AssetsAmount);


}
  // returns the CIK 
public static String CIK(String ticker) throws Exception
{
// concatenate the user input with the url
String myUrl=myUrlTemp+ticker;
// Connect to the URL and store the response in a Buffer Reader
  URL url=new URL(myUrl);

BufferedReader reader=new BufferedReader(new InputStreamReader(url.openStream()));
// This step is to convert the BufferedReader to String. It's easy and efficient to parse XML in String.
  String response=new String();
for(String l;(l=reader.readLine())!=null;response+=l);

  // value is the filter for XPath so it knows which tags to check
String value="/dataRequest/tickerLookup/cik";
  // call to XP function which returns the amount stored in the tags
String cik=XP(response,value);

return cik;
}
  // This function uses XPath to search in String and get the Element value we are after.
public static String XP(String response,String value) throws Exception
{
XPathFactory xpathFactory = XPathFactory.newInstance();
XPath xpath = xpathFactory.newXPath();
InputSource source = new InputSource(new StringReader(response));
String status = xpath.evaluate(value, source);
return status;

}
public static String XBValues(String cik)throws Exception
{
String myUrl=myUrlTemp2+cik;
URL url=new URL(myUrl);
BufferedReader reader=new BufferedReader(new InputStreamReader(url.openStream()));
String response=new String();
for(String l;(l=reader.readLine())!=null;response+=l);
String value="/dataRequest/fact/amount";
String amount =XP(response,value);
return amount;

}
}

