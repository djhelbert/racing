package com.racing.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.*;
import com.racing.model.RaceType;
import com.racing.util.Constants;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class RaceTypeDaoDynamo implements RaceTypeDao {
    private AmazonDynamoDB amazonDynamoDB;

    private static final String DYNAMODB_TABLE_NAME = "race_type";

    public RaceTypeDaoDynamo() {
        this.initDynamoDbClient();
    }

    private void initDynamoDbClient() {
        this.amazonDynamoDB = AmazonDynamoDBClientBuilder.standard().withRegion(Constants.REGION).build();
    }

    @Override
    public List<RaceType> all() {
        final ScanResult results = amazonDynamoDB.scan(new ScanRequest(DYNAMODB_TABLE_NAME));

        return results.getItems().stream().map(m -> attributes(m)).collect(Collectors.toList());
    }

    @Override
    public RaceType get(Integer id) {
        final AttributeValue value = new AttributeValue();
        value.setN(id.toString());

        final Map<String, AttributeValue> map = Map.of("id", value);
        final GetItemRequest request = (new GetItemRequest()).withTableName(DYNAMODB_TABLE_NAME).withKey(map);
        final GetItemResult result = amazonDynamoDB.getItem(request);

        if(result.getItem() == null) {
            return null;
        }

        final RaceType type = attributes(result.getItem());

        return type;
    }

    private RaceType attributes(Map<String, AttributeValue> results) {
        final RaceType type = new RaceType();
        type.setId(new Integer(results.get("id").getN()));
        type.setType(results.get("type").getS());
        type.setDescription(results.get("description").getS());

        return type;
    }

}
