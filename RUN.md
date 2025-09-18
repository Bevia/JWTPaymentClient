curl -X POST http://localhost:9000/auth/login \                                                               ░▒▓ ✔ │ system ⬢ │ at 13:58:18  ▓▒░
-H "Content-Type: application/json" \
-d '{"username":"user","password":"password"}'

--> response:

{
"accessToken":"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiaXNzIjoibXNwLWF1dGgiLCJpYXQiOjE3NTgxOTY3NzcsImV4cCI6MTc1ODIwMDM3N30.KktbDp52wy-mt5tOVcQZb8XhzbheT2OLzm7VcloeEqY",
"refreshToken":"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiaXNzIjoibXNwLWF1dGgiLCJpYXQiOjE3NTgxOTY3NzcsImV4cCI6MTc1ODgwMTU3N30.mbfzgxZBCAkcsEFu8JGvpC0-TsGD6XjIQCO_15CZBkY"
}

--> then call payment:

curl http://localhost:9100/payments/echo \
-H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiaXNzIjoibXNwLWF1dGgiLCJpYXQiOjE3NTgxOTY3NzcsImV4cCI6MTc1ODIwMDM3N30.KktbDp52wy-mt5tOVcQZb8XhzbheT2OLzm7VcloeEqY"



--> to RUN:

export JWT_SECRET=$(openssl rand -base64 64)

# Run payment client
JWT_SECRET="$JWT_SECRET" ./gradlew bootRun --args='--server.port=9100'