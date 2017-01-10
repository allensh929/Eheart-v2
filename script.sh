STR=`ps -ef | grep tomcat |grep -v 'grep' |  awk '{print $2}'`
echo $STR
kill -9 $STR
rm -rf /opt/tomcat/apache-tomcat-8.5.9/webapps/ROOT/
rm apache-tomcat-8.5.9/logs/*
mv /opt/tomcat/apache-tomcat-8.5.9/eheart-0.0.1-SNAPSHOT.war /opt/tomcat/apache-tomcat-8.5.9/eheart-0.0.1-SNAPSHOT.war_back`date +%Y-%m-%d:%H:%M:%S`
cp /opt/tomcat/eheart-0.0.1-SNAPSHOT.war /opt/tomcat/apache-tomcat-8.5.9/
cd apache-tomcat-8.5.9/bin
./startup.sh
