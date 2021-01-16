#!/bin/bash

echo "* Request for authorization"
RESULT=`curl --data "username=admin&password=Pa55w0rd&grant_type=password&client_id=admin-cli" http://localhost:8080/auth/realms/master/protocol/openid-connect/token`

echo "\n"
echo "* Recovery of the token"
TOKEN=`echo $RESULT | sed 's/.*access_token":"//g' | sed 's/".*//g'`

echo "\n"
echo "* Display token"
echo $TOKEN

echo "\n"
echo " * user creation\n"
curl -v http://localhost:8080/auth/admin/realms/banking/users -H "Content-Type: application/json" -H "Authorization: bearer $TOKEN"   --data '{"username":"psusarla","firstName":"Phani","lastName":"Susarla", "email":"phani.susarla@gmail.com", "enabled":"true"}'
