package net.jstgo.db.translators;

import net.jstgo.db.abstracts.DatabaseProperties;
import net.jstgo.db.enums.AggregateFunction;
import net.jstgo.db.enums.ComparisonOperator;
import net.jstgo.db.enums.ConditionalOperator;
import net.jstgo.db.enums.FunctionOperator;
import net.jstgo.db.enums.OrderByType;
import net.jstgo.db.enums.TableJoinerType;
import net.jstgo.db.query.QueryBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MysqlPropertiesTest {

  // CHANGE THE PROPERTIES CLASS TO TRANSLATE TO OTHER QUERY LANGUAGES
  private final DatabaseProperties properties = new MysqlProperties();

  @Test
  void selectQueryWithConditions() {
    var b = new QueryBuilder();
    var query = QueryBuilder.newSelect();
    query
        .select(
            b.field("user", "username"),
            b.field("user", "password"))
        .from(
            b.field("user")
        )
        .where(
            b.condition(
                "user",
                "username",
                ComparisonOperator.EQUAL,
                ConditionalOperator.OR,
                b.value("Administrator")),
            b.condition(
                "user",
                "password",
                ComparisonOperator.NOT_EQUAL,
                b.value("otherPasswords")));
    System.out.println(properties.translateQuery(query));
    Assertions.assertNotNull(properties.translateQuery(query));
  }

  @Test
  void selectQueryWithJoins() {
    var b = new QueryBuilder();
    var query = QueryBuilder.newSelect();
    var subQuery = QueryBuilder.newSelect();
    subQuery
        .select(b.field("role", "name"))
        .from(b.field("role"))
        .where(b.condition(
            "role",
            "name",
            ComparisonOperator.EQUAL,
            b.value("admin")));

    query
        .distinct(true)
        .select(
            b.function(AggregateFunction.ADD,
                b.function(AggregateFunction.COUNT, b.value("some")),
                b.fieldJoiner(
                    b.value(12, FunctionOperator.ADD),
                    b.fieldJoiner(true,
                        b.value(15, FunctionOperator.DIV),
                        b.value(20)))),
            b.function(AggregateFunction.EXISTS, b.query(subQuery)),
            b.field("user", "password"))
        .from(
            b.field("users"),
            b.tableJoiner(
                b.onJoiner(
                    TableJoinerType.LEFT,
                    b.field("role"),
                    b.fieldJoiner(false,
                        b.condition("user", "id",
                            ComparisonOperator.EQUAL,
                            ConditionalOperator.AND,
                            b.field("role", "id")),
                        b.condition("user", "deleted",
                            ComparisonOperator.EQUAL,
                            ConditionalOperator.AND,
                            b.value(0))
                    )),
                b.onJoiner(
                    TableJoinerType.INNER,
                    b.field("sales"),
                    b.field("user", "id",
                        ComparisonOperator.EQUAL,
                        b.field("sale", "id")))
            )
        )
        .where(
            b.condition(
                "user",
                "username",
                ComparisonOperator.EQUAL,
                ConditionalOperator.OR,
                b.value("Administrator")),
            b.condition(
                "user",
                "password",
                ComparisonOperator.NOT_EQUAL,
                b.value("otherPasswords")))
        .limit(15);
    System.out.println(properties.translateQuery(query));
    Assertions.assertNotNull(properties.translateQuery(query));
  }

  @Test
  void selectQueryWithGroupAndOrder() {
    var b = new QueryBuilder();
    var query = QueryBuilder.newSelect();
    query
        .distinct(true)
        .select(
            b.field("user", "username")
        )
        .from(
            b.field("user")
        )
        .where(
            b.condition("user",
                "username",
                ComparisonOperator.EQUAL,
                b.value())
        )
        .groupBy(
            b.field("name"),
            b.field("type")
        )
        .orderBy(
            b.order(b.field("username"), OrderByType.ASC),
            b.order(b.field("type"), OrderByType.DESC)
        );
    System.out.println(properties.translateQuery(query));
    Assertions.assertNotNull(properties.translateQuery(query));
  }

  @Test
  void selectQueryExample2() {
    var builder = new QueryBuilder();
    var selectQuery = QueryBuilder.newSelect();
    selectQuery =
        QueryBuilder.newSelect()
            .select(
                builder.field("id"),
                builder.field("deleted")
            )
            .from(
                builder.field("session"),
                builder.tableJoiner(
                    builder.onJoiner(
                        TableJoinerType.INNER,
                        builder.fieldAs("user", "a"),
                        builder.fieldJoiner(
                            false,
                            builder.condition("a", "id",
                                ComparisonOperator.EQUAL,
                                ConditionalOperator.AND,
                                builder.field("session", "user_id")
                            )
                        )
                    )
                )
            )
            .where(
                builder.condition(
                    "session",
                    "id",
                    ComparisonOperator.EQUAL,
                    builder.value(false)),
                builder.condition(
                    "session",
                    "expiration",
                    ComparisonOperator.GREATER_OR_EQUAL,
                    builder.value()),
                builder.condition(
                    "session",
                    "deleted",
                    ComparisonOperator.EQUAL,
                    builder.value())
            );
    System.out.println(properties.translateQuery(selectQuery));
    Assertions.assertNotNull(properties.translateQuery(selectQuery));
  }

  @Test
  void deleteQueryWithCondition() {
    var b = new QueryBuilder();
    var query = QueryBuilder.newDelete();
    query
        .from(
            b.field("user")
        )
        .where(
            b.condition("user",
                "username",
                ComparisonOperator.EQUAL,
                b.value("some"))
        );
    System.out.println(properties.translateQuery(query));
    Assertions.assertNotNull(properties.translateQuery(query));
  }

  @Test
  void insertQueryWithValues() {
    var b = new QueryBuilder();
    var query = QueryBuilder.newInsert();
    QueryBuilder.newInsert()
        .insert(b.field("user"))
        .fields(b.field("username"), b.field("type"))
        .addValue(b.value("Administrator"), b.value("admin"));
    System.out.println(properties.translateQuery(query));
    Assertions.assertNotNull(properties.translateQuery(query));
  }

  @Test
  void insertQueryWithValueOfSelectQuery() {
    var b = new QueryBuilder();
    var select = QueryBuilder.newSelect();
    select
        .select(b.field("role", "name"))
        .from(b.field("role"))
        .where(b.condition(
            "role",
            "name",
            ComparisonOperator.EQUAL,
            b.value("admin")));

    var insert = QueryBuilder.newInsert();
    insert
        .insert(b.field("user"))
        .fields(b.field("username"), b.field("type"))
        .values(select);
    System.out.println(properties.translateQuery(insert));
    Assertions.assertNotNull(properties.translateQuery(insert));
  }

  @Test
  void updateQueryWithCondition() {
    var b = new QueryBuilder();
    var update = QueryBuilder.newUpdate(b.field("user"));
    update.fields(
        b.field("user", "username", b.value()),
        b.field("user", "password", b.value())
    ).where(b.condition(
        "user",
        "id",
        ComparisonOperator.EQUAL,
        b.value()));
    System.out.println(properties.translateQuery(update));
    Assertions.assertNotNull(properties.translateQuery(update));
  }
}