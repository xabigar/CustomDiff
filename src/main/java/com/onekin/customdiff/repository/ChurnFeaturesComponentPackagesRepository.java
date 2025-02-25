package com.onekin.customdiff.repository;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import com.onekin.customdiff.model.ChurnFeaturesPackageAssets;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.onekin.customdiff.model.ChurnFeaturesAndPackagesGrouped;
import com.onekin.customdiff.model.ChurnFeaturesComponentPackages;

@Transactional
public interface ChurnFeaturesComponentPackagesRepository extends CrudRepository<ChurnFeaturesComponentPackages, Long> {

    Iterable<ChurnFeaturesComponentPackages> getCustomsByIdproductrelease(int id_productrelease);

    Iterable<ChurnFeaturesComponentPackages> getCustomsByIdfeature(String idfeature);

    @Query(value = "SELECT new ChurnFeaturesAndPackagesGrouped(c.id, c.idfeature, c.featurename, c.package_name, c.idpackage, SUM(c.churn)) FROM ChurnFeaturesComponentPackages c WHERE c.idfeature!='No Feature' GROUP BY c.idfeature, c.idpackage")
    Iterable<ChurnFeaturesAndPackagesGrouped> getCustomsGroupByFeatures();

    List<ChurnFeaturesComponentPackages> findByIdpackage(int packageId);

    @Query(value = "SELECT new ChurnFeaturesAndPackagesGrouped(c.id, c.idfeature, c.featurename, c.package_name, c.idpackage, SUM(c.churn)) FROM ChurnFeaturesComponentPackages c WHERE c.idfeature in (:featureIds)GROUP BY c.idfeature, c.idpackage")
    Iterable<ChurnFeaturesAndPackagesGrouped> findByIdfeatureIn(@Param("featureIds") Set<String> featureIds);

    @Query(value = "SELECT new ChurnFeaturesAndPackagesGrouped(c.id, c.idfeature, c.featurename, c.package_name, c.idpackage, SUM(c.churn)) FROM ChurnFeaturesComponentPackages c WHERE c.idfeature in (:featureIds) AND c.idpackage in (:packageIds) GROUP BY c.idfeature, c.idpackage")
    List<ChurnFeaturesAndPackagesGrouped> findByIdfeatureInAndIdpackageIn(@Param("featureIds") Set<String> featureIds,
                                                                          @Param("packageIds") Set<Integer> rightPackageIds);

    @Query(value = "SELECT new ChurnFeaturesAndPackagesGrouped(c.id, c.idfeature, c.featurename, c.package_name, c.idpackage, SUM(c.churn)) FROM ChurnFeaturesComponentPackages c WHERE c.idfeature!='No Feature' and c.idpackage= :idPackage GROUP BY c.idfeature")
    List<ChurnFeaturesAndPackagesGrouped> findByIdPackageGroupByFeatures(@Param("idPackage") Integer idPackage);

    @Query(value = "SELECT new ChurnFeaturesAndPackagesGrouped(c.id, c.idfeature, c.featurename, c.package_name, c.idpackage, SUM(c.churn)) FROM ChurnFeaturesComponentPackages c WHERE c.idfeature IN (:featureIds) and c.idpackage= :idPackage GROUP BY c.idfeature")
    List<ChurnFeaturesAndPackagesGrouped> findByIdPackageAndFeaturesInGroupByFeatures(@Param("idPackage") Integer idPackage,
                                                                                      @Param("featureIds") Set<String> featureIds);

    @Query(value = "SELECT new ChurnFeaturesAndPackagesGrouped(c.id, c.idfeature, c.featurename, c.package_name, c.idpackage, SUM(c.churn)) FROM ChurnFeaturesComponentPackages c WHERE c.idfeature in (:featureIds) AND c.idpackage not in (:packageIds) GROUP BY c.idfeature, c.idpackage")
    List<ChurnFeaturesAndPackagesGrouped> findByIdfeatureInAndNotIdpackageIn(@Param("featureIds") Set<String> featureIds,
                                                                             @Param("packageIds") Set<Integer> rightPackageIds);


    @Query(value = "SELECT new ChurnFeaturesAndPackagesGrouped(c.id, c.idfeature, c.featurename, c.package_name, c.idpackage, SUM(c.churn)) FROM ChurnFeaturesComponentPackages c WHERE c.idpackage IN (:packageIds) and c.idparentfeature= :parentFeatureId GROUP BY c.idfeature")
    List<ChurnFeaturesAndPackagesGrouped> findByParentFeatureAndPackageIdsInAndGroupByFeatures(
            @Param("packageIds") Set<Integer> packageIds, @Param("parentFeatureId") int parentFeatureId);

    @Query(value = "SELECT new ChurnFeaturesAndPackagesGrouped(c.id, c.idfeature, c.featurename, c.package_name, c.idpackage, SUM(c.churn)) FROM ChurnFeaturesComponentPackages c WHERE c.idpackage IN (:packageIds) and c.idfeature= :featureId GROUP BY c.idfeature")
    List<ChurnFeaturesAndPackagesGrouped> findByPackageIdInAndFeaturesGroupedByFeaturesAndAsset(
            @Param("packageIds") Set<Integer> packageIds, @Param("featureId") String featureId);

}
