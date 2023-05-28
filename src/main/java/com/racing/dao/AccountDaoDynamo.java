package com.racing.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.*;
import com.racing.model.Account;
import com.racing.util.Constants;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class AccountDaoDynamo implements AccountDao {
    private AmazonDynamoDB amazonDynamoDB;
    private static final String DYNAMODB_TABLE_NAME = "Account";

    public AccountDaoDynamo() {
        this.initDynamoDbClient();
    }

    private void initDynamoDbClient() {
        this.amazonDynamoDB = AmazonDynamoDBClientBuilder.standard().withRegion(Constants.REGION).build();
    }

    @Override
    public List<Account> all() {
        final ScanResult results = amazonDynamoDB.scan(new ScanRequest(DYNAMODB_TABLE_NAME));

        return results.getItems().stream().map(m -> attributes(m)).collect(Collectors.toList());
    }

    @Override
    public Account get(String id) {
        final AttributeValue value = new AttributeValue(id);
        final Map<String, AttributeValue> map = Map.of("id", value);
        final GetItemRequest request = (new GetItemRequest()).withTableName(DYNAMODB_TABLE_NAME).withKey(map);
        final GetItemResult result = amazonDynamoDB.getItem(request);

        if (result.getItem() == null) {
            return null;
        }

        return attributes(result.getItem());
    }

    @Override
    public Account getByEmail(String email) {
        final Map<String, AttributeValue> map = Map.of("email", new AttributeValue(email));
        final GetItemRequest request = (new GetItemRequest()).withTableName(DYNAMODB_TABLE_NAME).withKey(map);
        final GetItemResult result = amazonDynamoDB.getItem(request);

        if (result.getItem() == null) {
            return null;
        }

        return attributes(result.getItem());
    }

    private Account attributes(Map<String, AttributeValue> results) {
        final Account account = new Account();
        account.setFirst(results.get("first").getS());
        account.setLast(results.get("last").getS());
        account.setEmail(results.get("email").getS());
        account.setPhone(results.get("phone").getS());
        account.setPassword(results.get("password").getS());
        account.setActive(results.get("active").getBOOL());

        return account;
    }
}
