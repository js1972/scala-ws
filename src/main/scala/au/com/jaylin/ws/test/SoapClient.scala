package au.com.jaylin.ws.test

import scala.xml.{Elem, XML}
import scala.xml.PrettyPrinter
import org.apache.commons.codec.binary.Base64


class SoapClient {
    private def error(msg: String) = {
        println("SoapClient error: " + msg)
    }
    
    def wrap(xml: Elem): String = {
        val buf = new StringBuilder
        buf.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n")
        buf.append("<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">\n")
        buf.append("<SOAP-ENV:Body>\n")
        buf.append(xml.toString)
        buf.append("\n</SOAP-ENV:Body>\n")
        buf.append("</SOAP-ENV:Envelope>\n")
        buf.toString
    }
    
    def sendMessage(host: String, req: Elem, user: String, pass: String): Option[Elem] = {
        val url = new java.net.URL(host)
        val outs = wrap(req).getBytes()
        val conn = url.openConnection.asInstanceOf[java.net.HttpURLConnection]
        try {
            conn.setRequestMethod("POST")
            conn.setDoOutput(true)
            conn.setRequestProperty("Content-Length", outs.length.toString)
            conn.setRequestProperty("Content-Type", "text/xml")
            val userpass = user + ":" + pass
            val basicAuth = "Basic " + new String(new Base64().encode(userpass.getBytes()))
            conn.setRequestProperty("Authorization", basicAuth)
            conn.getOutputStream().write(outs)
            conn.getOutputStream().close()
            Some(XML.load(conn.getInputStream()))
        }
        catch {
          case e: Exception => error("post: " + e)
              error("post:" + scala.io.Source.fromInputStream(conn.getErrorStream).mkString)
              None
        }
    }
}

object SoapTest {
    def doTest {
        val host = "http://app1poy.inpex.com.au:58000/CommunicationChannelService/HTTPBasicAuth?style=document"
        val req  = <pns:read xmlns:pns="urn:CommunicationChannelServiceVi">
                       <yq1:CommunicationChannelReadRequest xmlns:yq1="urn:CommunicationChannelServiceVi" xmlns:pns="urn:com.sap.aii.ibdir.server.api.types">
                           <pns:CommunicationChannelID>
                               <pns:ComponentID>BC_Jason</pns:ComponentID>
                               <pns:ChannelID>ERP_PurchaseOrders_Test_R_FILE</pns:ChannelID>
                           </pns:CommunicationChannelID>
                       </yq1:CommunicationChannelReadRequest>
                   </pns:read>

        val cli = new SoapClient
        println("##### request:\n" + cli.wrap(req))
        val auth = ("jscott", "sophie05")
        val resp = cli.sendMessage(host, req, auth _1, auth _2)
        if (resp.isDefined) {
            println("##### response:\n")
            val pp = new PrettyPrinter(80, 4)
            println(pp.format(resp.get))
        }
    }
    
    def main(args: Array[String]) {
        doTest
    }
}