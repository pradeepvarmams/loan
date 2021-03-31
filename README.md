# laon

Post service curl command:

curl --location --request POST 'http://localhost:8080/newloan' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name" : "megh",
    "ssn": "3127357",
    "rate": 3,
    "dob": "07/19/1992",
    "principal": 1.2,
    "type":"mortgage",
    "term":10
}

Get service curl command:
curl --location --request GET 'http://localhost:8080/retrieveLoan/megh'
