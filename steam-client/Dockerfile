FROM java:openjdk-8-jre

ENV APP_HOME=/app/home

ADD target/*jar /opt/cryptobeton/

CMD java -cp "/opt/cryptobeton/*" ru.ir.steam.client.App -Dapp.home=APP_HOME