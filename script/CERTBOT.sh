PWD_DIR=`pwd`

echo "$PWD"

rm -rf $PWD/etc
rm -rf $PWD/var

# sleep 1

docker run -it --rm --name certbot \
    -v '$PWD/etc/letsencrypt:/etc/letsencrypt' \
    -v '$PWD/var/lib/letsencrypt:/var/lib/letsencrypt' \
    certbot/dns-google \
    certonly -d 'swim-playground.com' -d '*.swim-playground.com' \
    -m swsw1005@gmail.com \
    --agree-tos \
    --manual --preferred-challenges dns \
    --server https://acme-v02.api.letsencrypt.org/directory


# echo "==== OLD CERT ==================================="

# md5sum /root/pem/
# ls -alh /root/pem/

# cp $PWD/etc/letsencrypt/archive/*.pem /root/pem/

# echo "==== NEW CERT ! ================================="

# md5sum /root/pem/
# ls -alh /root/pem/