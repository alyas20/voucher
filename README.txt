This application voucher only need to run from intelliJ community edition. DB using MYSQL 8. Java using java 21 JDK. Please have prepare this two environment first and check the url DB same.
Just run the application and it will generate schema and also tables. After start running, it will added some data that I been add into dataloader. After finish you 
can use Spring Doc to test the API. The url as Below. I provide some example for the API request. And you will see the responds at the same spot. Please reach me if 
got any problem. Thank you.

Spring Doc:

http://localhost:8080/swagger-ui/index.html#/

Test Data:


/api/vouchers/generateVoucher (To generate Voucher code by specialOffer and emails that has been exist in DB *already added some email)

{
  "specialOffer": "Hari Pekerja",
  "expDate": "2025-05-19",
  "emails": [
    "alyasamsyar@gmail.com",
    "alyasamsyar@hotmail.com"
  ]
}

/api/vouchers/validateVoucher (To validate voucher by email and voucher code)

voucherCode : that has been generated (Check in DB)
email: "alyasamsyar@gmail.com"/"alyasamsyar@hotmail.com"



/api/vouchers/getValidVouchers (To give a list of valid voucher by email)

email: "alyasamsyar@gmail.com"/"alyasamsyar@hotmail.com"