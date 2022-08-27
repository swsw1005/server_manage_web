#! /bin/sh

HOME_DIR=/usr/local/server-manager

# mv server-manager*.war  server-manager.war

systemctl disable server_manager
systemctl disable server-manager
systemctl disable server_manage
systemctl disable server-manage

systemctl stop server_manager
systemctl stop server-manager
systemctl stop server_manage
systemctl stop server-manage

rm /etc/systemd/system/server-manager.service
rm /etc/systemd/system/server_manager.service
rm /etc/systemd/system/server-manage.service
rm /etc/systemd/system/server_manage.service

mv $HOME_DIR/server-manager.service /etc/systemd/system/

systemctl daemon-reload

systemctl enable server-manager

systemctl stop server-manager
systemctl start server-manager

systemctl status server-manager


