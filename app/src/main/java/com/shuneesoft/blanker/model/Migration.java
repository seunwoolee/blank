package com.shuneesoft.blanker.model;

import androidx.annotation.Nullable;

import io.realm.DynamicRealm;
import io.realm.DynamicRealmObject;
import io.realm.FieldAttribute;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;

/**
 * Example of migrating a Realm file from version 0 (initial version) to its last version (version 3).
 */
public class Migration implements RealmMigration {

    @Override
    public int hashCode() {
        return Migration.class.hashCode();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null) {
            return false;
        }
        return obj instanceof Migration;
    }

    @Override
    public void migrate(final DynamicRealm realm, long oldVersion, long newVersion) {
//        RealmSchema schema = realm.getSchema();
//        if (oldVersion == 0) {
//            RealmObjectSchema articleSchema = schema.get("Article");
//            articleSchema.addField("content2", String.class);
//            oldVersion++;
//        }

//        if (oldVersion == 1) {
//            RealmObjectSchema articleSchema = schema.get("Article");
//            articleSchema.removeField("content2");
//            oldVersion++;
//        }
//
//        if (oldVersion == 2) {
//            RealmObjectSchema articleSchema = schema.get("Article");
//            articleSchema.addField("content2", String.class)
//                    .transform(obj
//                -> obj.set("content2", obj.getString("title") + " " + obj.getString("content")));
//            oldVersion++;
//        }

    }
}