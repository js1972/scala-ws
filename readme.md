scala-ws
==========

Simple test program for calling a SOAP web service in Scala.
Does not rely on a wsdl; sends the raw payload (much simpler).

To run this sample app create an application.conf file in the src/main/resources/ directory. This file simply allows me to remove the hostname, user and password from the source. This file should contain configuration entries as follows:

  config {
    host="http://<hostname>:<port>"
    user="<basic_auth_username>"
    pass="basic_auth_password"
  }
