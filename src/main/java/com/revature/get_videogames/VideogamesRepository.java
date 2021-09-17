package com.revature.get_videogames;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class VideogamesRepository {

    private final DynamoDBMapper dbReader;

    public VideogamesRepository() { dbReader = new DynamoDBMapper(AmazonDynamoDBClientBuilder.defaultClient()); }

    public List<Videogames> getAllVideogames() { return dbReader.scan(Videogames.class, new DynamoDBScanExpression()); }

    public Optional<Videogames> getVideogamesByGame(String title) {

        Map<String, AttributeValue> queryInputs = new HashMap<>();
        queryInputs.put("game", new AttributeValue().withS(title));

        DynamoDBScanExpression query = new DynamoDBScanExpression()
                .withFilterExpression("game = :game")
                .withExpressionAttributeValues(queryInputs);
        List<Videogames> queryResult = dbReader.scan(Videogames.class, query);

        return queryResult.stream().findFirst();

    }
}
