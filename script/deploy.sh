echo "==============================================="
echo "   START DEPLOY !!!!"
echo "==============================================="

sftp -v -i /root/.ssh/id_rsa/id_rsa_220619_1448 root@192.168.0.21  <<EOF
cd /usr/local/server-manager
put  logback.xml  /usr/local/server-manager
put  service/server-manager.service  /usr/local/server-manager
put  service/install.sh  /usr/local/server-manager
put  application.properties  /usr/local/server-manager
put  ../target/server-manager-1.0.0.war  /usr/local/server-manager
exit
EOF

sleep 1

echo "==============================================="
echo "   FILE MV END !!!!"
echo "==============================================="

ssh -i /root/.ssh/id_rsa/id_rsa_220619_1448 root@192.168.0.21 \
 'chmod 755 /usr/local/server-manager/install.sh'

sleep 1

ssh -i /root/.ssh/id_rsa/id_rsa_220619_1448 root@192.168.0.21 \
 'sh /usr/local/server-manager/install.sh'

 sleep 1

echo "==============================================="
echo "   DEPLOY MV END !!!!"
echo "==============================================="

