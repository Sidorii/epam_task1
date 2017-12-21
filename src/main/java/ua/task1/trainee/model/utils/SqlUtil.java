package ua.task1.trainee.model.utils;

public class SqlUtil {

    public static String generateInClause(int elementsCount) {
        StringBuilder sb = new StringBuilder(" IN (");
        for (int i = 0; i < elementsCount - 1; i++) {
            sb.append("?,");
        }
        sb.append("?)");
        return sb.toString();
    }
}
