#! /bin/bash

systemctl disable server_manager
systemctl disable server-manager
systemctl disable server_manage
systemctl disable server-manage

rm /etc/systemd/system/server-manager.service
rm /etc/systemd/system/server_manager.service
rm /etc/systemd/system/server-manage.service
rm /etc/systemd/system/server_manage.service

cp ./*.service /etc/systemd/system/

systemctl daemon-reload

systemctl enable server-manager

systemctl stop server-manager
systemctl start server-manager

systemctl status server-manager
