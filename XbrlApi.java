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
{ final static String myUrlTemp="https://csuite.xbrl.us/php/dispatch.php?Task=xbrlCIKLookup&%20Ticker=";
  final static String myUrlTemp2="https://csuite.xbrl.us/php/dispatch.php?Task=xbrlValues&Element=Assets&Year=2014&Period=Y&API_Key=4b979bba-7f27-4643-9d52-6b2cdb5a924a&CIK=";
public static void main(String[] args) throws Exception
{

Scanner kb=new Scanner(System.in);
System.out.println("Enter the Ticker ");
String ticker=kb.nextLine();

String cik=CIK(ticker);
System.out.println("Cik is: "+cik);

String AssetsAmount=XBValues(cik);
System.out.println("Assests Amount is: "+AssetsAmount);


}
public static String CIK(String ticker) throws Exception
{

String myUrl=myUrlTemp+ticker;
URL url=new URL(myUrl);

BufferedReader reader=new BufferedReader(new InputStreamReader(url.openStream()));
String response=new String();
for(String l;(l=reader.readLine())!=null;response+=l);
String value="/dataRequest/tickerLookup/cik";
String cik=XP(response,value);

return cik;
}
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

