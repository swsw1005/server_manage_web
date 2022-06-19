TARGET_IP=192.168.0.21


echo "==============================================="
echo "   START DEPLOY !!!!"
echo "==============================================="

sftp -v -i /root/.ssh/id_rsa/id_rsa_220619_1448 root@$TARGET_IP  <<EOF
cd /usr/local/server-manager
put  logback.xml  /usr/local/server-manager
put  service/server-manager.service  /usr/local/server-manager
put  service/install.sh  /usr/local/server-manager
put  application.properties  /usr/local/server-manager
put  ../target/server-manager-1.0.0.war  /usr/local/server-manager
exit
EOF

echo "==============================================="
echo "   FILE MV END !!!!"
echo "==============================================="

ssh -i /root/.ssh/id_rsa/id_rsa_220619_1448 root@$TARGET_IP \
 'chmod 755 /usr/local/server-manager/install.sh'

ssh -i /root/.ssh/id_rsa/id_rsa_220619_1448 root@$TARGET_IP \
 'sh /usr/local/server-manager/install.sh'

echo "==============================================="
echo "   DEPLOY MV END !!!!"
echo "==============================================="

