package com.racing.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.*;
import com.racing.model.Organizer;
import com.racing.util.Constants;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OrganizerDaoDynamo implements OrganizerDao {
    private AmazonDynamoDB amazonDynamoDB;

    private static final String DYNAMODB_TABLE_NAME = "Organizer";

    public OrganizerDaoDynamo() {
        this.initDynamoDbClient();
    }

    private void initDynamoDbClient() {
        this.amazonDynamoDB = AmazonDynamoDBClientBuilder.standard().withRegion(Constants.REGION).build();
    }

    @Override
    public List<Organizer> getByTypeOrState(String type, String state) {
        final Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
        expressionAttributeValues.put(":val1", new AttributeValue().withS(type));
        expressionAttributeValues.put(":val2", new AttributeValue().withS(state));

        final ScanRequest scanRequest = new ScanRequest()
                .withTableName(DYNAMODB_TABLE_NAME)
                .withFilterExpression("contains(states, :val2) AND raceType = :val1")
                .withExpressionAttributeValues(expressionAttributeValues);

        final List<Organizer> list = new ArrayList<>();
        final ScanResult result = amazonDynamoDB.scan(scanRequest);

        for (Map<String, AttributeValue> item : result.getItems()) {
            list.add(attributes(item));
        }

        return list;
    }

    @Override
    public Organizer get(Integer id) {
        final AttributeValue value = new AttributeValue();
        value.setN(id.toString());

        final Map<String, AttributeValue> map = Map.of("id", value);
        final GetItemRequest request = (new GetItemRequest()).withTableName(DYNAMODB_TABLE_NAME).withKey(map);
        final GetItemResult result = amazonDynamoDB.getItem(request);

        if (result.getItem() == null) {
            return null;
        }

        return attributes(result.getItem());
    }

    private Organizer attributes(Map<String, AttributeValue> results) {
        final Organizer org = new Organizer();
        org.setId(new Integer(results.get("id").getN()));
        org.setRaceType(results.get("raceType").getS());
        org.setUrl(results.get("url").getS());
        org.setDescription(results.get("description").getS());
        org.setName(results.get("name").getS());
        org.setStates(results.get("states").getSS());

        return org;
    }
}
