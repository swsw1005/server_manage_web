#! /bin/bash

cp ./*.service /etc/systemd/system/

systemctl daemon-reload

systemctl enable server_manager

systemctl stop server_manager
systemctl start server_manager

systemctl status server_manager