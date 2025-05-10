# MS-Bank - Banking Application

A lightweight Java Spring Boot-based banking system that allows onboarding new customers, managing accounts (Current and Savings), performing internal transfers, making external payments, and tracking transactions — all built with RESTful APIs and H2 in-memory database support.

---

## 🚀 Getting Started

### 📦 GitHub Actions & JAR Download

This repository is integrated with GitHub Actions. Every time a PR is merged into the `master` branch:
- The GitHub workflow runs test cases and builds the project.
- The compiled JAR is uploaded under **GitHub Actions → Artifact → `package`**.
- You can **download the latest JAR** from there directly.

---

### 🔧 Running the Application

1. **Download the JAR**
   From the GitHub Actions artifact section.

2. **Run the JAR:**
   ```bash
   java -jar <jar-name>.jar
   ```

3. **Access APIs Locally:**
   The app will run on:
   ```
   http://localhost:8080
   ```

4. **Use with Postman / Swagger:**
   You can make API calls using tools like Postman.

---

## 🗄️ Database

This app uses an **H2 in-memory database**.

- No data is persisted between sessions.
- You must **start by onboarding a new customer** to use the app.
- Every time you restart the JAR, the database resets. You’ll need to repeat the onboarding flow.
- Access Database locally 
```
   http://localhost:8080/h2-console
 ```
---

## 🧪 API Endpoints & Sample Payloads

### ✅ Onboarding a Customer

**POST** `/api/customer/onBoardNewCustomer`

**Request:**
```json
{
    "name": "Megharaj",
    "email": "megharaj.salagar@gmail.com"
}
```

**Response:**
```json
{
  "success": true,
  "status": 201,
  "message": "Customer Details successfully created",
  "timestamp": "2025-05-09T22:38:28.705806",
  "data": {
    "id": 1,
    "name": "Megharaj",
    "email": "megharaj.salagar@gmail.com"
  },
  "errorCode": null
}
```

---

### ✅ Get Customer Details

**GET** `/api/customer/getCustomer/{customerId}`

**Response:**
```json
{
  "success": true,
  "status": 200,
  "message": "Customer Details fetched successfully",
  "timestamp": "2025-05-09T22:38:33.840253400",
  "data": {
    "id": 1,
    "name": "Megharaj",
    "email": "megharaj.salagar@gmail.com"
  },
  "errorCode": null
}
```

---

### ✅ Get Customer Accounts

**GET** `/api/customer/getCustomer/{customerId}/accounts`

**Response:**
```json
{
  "success": true,
  "status": 200,
  "message": "Customer Details fetched successfully",
  "timestamp": "2025-05-09T22:38:37.706064700",
  "data": [
    {
      "id": 1,
      "customerId": 1,
      "type": "CURRENT",
      "balance": 1000.00
    },
    {
      "id": 2,
      "customerId": 1,
      "type": "SAVING",
      "balance": 500.00
    }
  ],
  "errorCode": null
}
```

---

### ✅ Transfer Between Accounts

**POST** `/api/accounts/transfer`

**Request:**
```json
{
  "transactionType": "TRANSFER_INTERNAL",
  "amount": 100,
  "fromAccountId" : 1,
  "toAccountId" : 2
}
```

**Response:**
```json
{
  "success": true,
  "status": 201,
  "message": "Transfer Successfully Completed",
  "timestamp": "2025-05-09T22:38:41.652145400",
  "data": {
    "transactionType": "TRANSFER_INTERNAL",
    "amount": 100,
    "fromAccountId": 1,
    "toAccountId": 2
  },
  "errorCode": null
}
```

---

### ✅ External Payment

**POST** `/api/accounts/externalPayment`

**Request:**
```json
{
  "fromAccountId": 1,
  "amount": 100,
  "description": "Electricity bill"
}
```
**Response:**
```json
{
  "success": true,
  "status": 201,
  "message": "Transfer Successfully Completed",
  "timestamp": "2025-05-09T22:38:45.829295600",
  "data": {
    "fromAccountId": 1,
    "amount": 100,
    "description": "Electricity bill"
  },
  "errorCode": null
}
```

---

### ✅ View Transactions

**GET** `/api/transaction/{accountId}`

**Response:**
```json
{
  "success": true,
  "status": 200,
  "message": "Transaction Details fetched successfully",
  "timestamp": "2025-05-09T22:39:03.428003300",
  "data": [
    {
      "id": 2,
      "accountId": 1,
      "type": "TRANSFER_OUT",
      "amount": -100.00,
      "fee": 0.05,
      "interest": null,
      "description": "Transfer to account 2(Fee:0.0500)",
      "timestamp": "2025-05-09T22:38:41.646103"
    },
    {
      "id": 4,
      "accountId": 1,
      "type": "PAYMENT",
      "amount": -100.00,
      "fee": 0.50,
      "interest": 0.00,
      "description": "Transaction from1successful",
      "timestamp": "2025-05-09T22:38:45.826118"
    }
  ],
  "errorCode": null
}
```

---

## 🔁 Notes

- ✅ **Interest (0.5%)** is credited to Savings Account on receiving transfers.
- ✅ **Payment Fee (0.05%)** is charged on external payments.
- ✅ **Only Current Account** can be used for payments and transfers.

---

## 👤 Author

**Megharaj Salagar**

Feel free to fork or contribute!