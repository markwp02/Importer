# importer
 Importer microservice for eCommerce project

### Active MQ
Install Docker for desktop 
Run the following command to get and run activemq image 

```shell
docker run -d --name activemq -p 61616:61616 -p 8161:8161 webcenter/activemq
```

Access the admin console http://localhost:8161/admin/ admin/admin

### Minio
Run the following command to run the minio image

```shell
docker run \
   -p 9000:9000 \
   -p 9001:9001 \
   --name minio1 \
   -v D:\minio\data:/data \
   -e "MINIO_ROOT_USER=ROOTUSER" \
   -e "MINIO_ROOT_PASSWORD=CHANGEME123" \
   quay.io/minio/minio server /data --console-address ":9001"
```

Change 
- D:\minio\data for the location you want to store your minio data locally
- MINIO_ROOT_USER to the root user you want
- MINIO_ROOT_PASSWORD to the root password you want to use

To connect to Minio set the following properties, by creating an access key and secret key pair.
> [!WARNING]
> Never publish your access key and secret access key to git

```
minio.access-key=qnS4D3myEhFttgeboI6P
minio.secret-access-key=9aNi6P7Y2wXuNOEpOgrN0VCRwivyMX54sgvdMOmN
```