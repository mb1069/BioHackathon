package org.elixir.marref.model

import org.elixir.marref.model.bioschema.{Property, TopLevel, ValueReference}
import org.elixir.marref.utils.json.Json

case class UrlModel(url: String, value: String)
/*case class DateModel(`type`: String, date: Option[String], yearMonth: Option[String], year: Option[String]) {
  lazy val value =
}*/
case class ValueModel(`type`: Option[String] = None, value: String)
case class ItemListModel(item: Seq[String])
case class AnnotationSoftwareRevisionModel(`type`: String, value: String)
case class AccessionModel(accession: Seq[UrlModel])
case class LatLongModel(latitude: Float,
                        longitude: Float) {
  lazy val str: String = s"lat:$latitude lon:$longitude"
}

case class SampleModel(annotationProvider: Option[String] = None,
                       //annotationDate: Option[DateModel],
                       annotationPipeline: Option[String],
                       annotationMethod: Option[ItemListModel],
                       annotationSoftwareRevision: Option[AnnotationSoftwareRevisionModel],
                       featuresAnnotated: Option[ItemListModel],
                       refseqCds: Option[Int],
                       ncbiRefseqAccession: Option[AccessionModel],
                       genes: Option[Int],
                       cds: Option[Int],
                       pseudoGenes: Option[Int],
                       partialRrnas: Option[Map[String, Int]],
                       trnas: Option[Int],
                       ncrna: Option[Int],
                       frameshiftedGenes: Option[Int],
                       sequencingCenters: Option[String],
                       seqMeth: Option[ItemListModel],
                       assemblyAccession: Option[UrlModel],
                       assembly: Option[ItemListModel],
                       gcContent: Option[Int],
                       numReplicons: Option[Int],
                       genomeLength: Option[Int],
                       plasmids: Option[Int],
                       hostCommonName: Option[String],
                       investigationType: Option[String],
                       sampleType: Option[String],
                       isolationSource: Option[String],
                       isolationCountry: Option[String],
                       envBiome: Option[UrlModel],
                       envFeature: Option[UrlModel],
                       envMaterial: Option[UrlModel],
                       envPackage: Option[String],
                       isolGrowthCondt: Option[AccessionModel],
                       refBiomaterial: Option[AccessionModel],
                       cultureCollection: Option[ItemListModel],
                       biosampleAccession: Option[UrlModel],
                       isolationComments: Option[String],
                       geoLocNameGaz: Option[String],
                       geoLocNameGazEnvo: Option[UrlModel],
                       analysisProjectType: Option[String],
                       fullScientificName: Option[String],
                       organism: Option[String],
                       taxonLineageNames: Option[ItemListModel],
                       taxonLineageIds: Option[AccessionModel],
                       ncbiTaxonIdentifier: Option[UrlModel],
                       strain: Option[ItemListModel],
                       kingdom: Option[String],
                       phylum: Option[String],
                       `class`: Option[String],
                       order: Option[String],
                       family: Option[String],
                       genus: Option[String],
                       species: Option[String],
                       cellShape: Option[String],
                       temperatureRange: Option[String],
                       oxygenRequirement: Option[String],
                       antismashTypes: Option[String],
                       antismashClusters: Option[String],
                       mmpID: Option[UrlModel],
                       curationDate: Option[String],
                       implementationDate: Option[String],
                       bioprojectAccession: Option[UrlModel],
                       genbankAccession: Option[AccessionModel],
                       silvaAccessionSSU: Option[UrlModel],
                       silvaAccessionLSU: Option[UrlModel],
                       uniprotAccession: Option[AccessionModel],
                       publicationPmid: Option[AccessionModel],
                       comments: Option[String],
                       baseID: Option[ValueModel],
                       genomeStatus: Option[String],
                       mmpBiome: Option[String],
                       sequencingDepth: Option[ItemListModel],
                       //collectionDate: Option[YearModel],
                       depth: Option[ValueModel],
                       latLon: Option[LatLongModel],
                       projectName: Option[String]
) {
  def toJson(): String = Json.serialize(this)
  def toBioschema(): TopLevel = {
    TopLevel(
      `@context` = "http://schema.org",
      `@type` = Seq("BioChemEntity","Sample"),
      identifier = Seq(s"mmp:${mmpID.map{_.value}.getOrElse{"NotDefined"}}", s"biosample:${biosampleAccession.map{_.value}.getOrElse("NotDefined")}"),
      name = fullScientificName.getOrElse("NotDefinded"),
      description = comments.getOrElse("NotDefined"),
      url = mmpID.map{_.url}.getOrElse("NotDefined"),
      dataset = Seq(), //Todo
      additionalProperty = Seq(
        Seq(Property(name = "Sequencing Depth",
          value = sequencingDepth.map{_.item.mkString(", ")}.getOrElse("NotFound")
        )),
        Seq(Property(name = "Sequencing Centers",
          value = sequencingCenters.getOrElse("NotFound")
        )),
        Seq(Property(name = "Comments",
          value = comments.getOrElse("NotFound")
        )),
        Seq(Property(name = "Isolation Comments",
          value = isolationComments.getOrElse("NotFound")
        )),
        Seq(Property(name = "Isolation Country",
          value = isolationCountry.getOrElse("NotFound")
        )),
        publicationPmid.toSeq.flatMap{_.accession}.map{a =>
          Property(name = "Publication PMID",
            value = a.value
          )
        },
        /*Property(name = "Collection Date",
          value = collectionDate.map{_.year}.getOrElse("NotFound")
        ),*/
        Seq(Property(name = "Depth",
          value = depth.map{_.value}.getOrElse("NotFound")
        )),
        Seq(Property(name = "Latitude and Longitude",
          value = latLon.map{_.str}.getOrElse("NotFound")
        )),
        Seq(Property(name = "Project Name",
          value = projectName.getOrElse("NotFound")
        )),
        Seq(Property(name = "Curation Date",
          value = curationDate.getOrElse("NotFound")
        )),
        Seq(Property(name = "MMP ID",
          value = mmpID.map{_.value}.getOrElse("NotFound")
        )),
        Seq(Property(name = "Silva Accession SSU",
          value = silvaAccessionSSU.map{_.value}.getOrElse("NotFound")
        )),
        Seq(Property(name = "Silva Accession LSU",
          value = silvaAccessionLSU.map{_.value}.getOrElse("NotFound")
        )),
        uniprotAccession.toSeq.flatMap{_.accession}.map{a =>
          Property(name = "Uniprot Accession",
            value = a.value
          )
        },
        Seq(Property(name = "ENA Assembly accession identifier",
          value = assemblyAccession.map{_.value}.getOrElse("NotFound")
        )),
        Seq(Property(name = "ENA BioProject accession identifier",
          value = bioprojectAccession.map{_.value}.getOrElse("NotFound")
        )),
        Seq(Property(name = "ENA BioSample accession identifier",
          value = biosampleAccession.map{_.value}.getOrElse("NotFound")
        )),
        genbankAccession.toSeq.flatMap{_.accession}.map{a =>
          Property(name = "ENA GenBank accession identifier",
            value = a.value
          )
        },
        ncbiRefseqAccession.toSeq.flatMap{_.accession}.map{a =>
          Property(name = "NCBI Refseq accession identifier",
            value = a.value
          )
        },
        cultureCollection.toSeq.flatMap{_.item.map{i =>
          Property(name = "Culture Collection(s)",
            value = i
          )
        }},
        Seq(Property(name = "Temperature Range",
          value = temperatureRange.getOrElse("NotFound")
        )),
        refBiomaterial.toSeq.flatMap{_.accession}.map{a =>
          Property(name = "Reference for Biomaterial",
            value = a.value)
        },
        Seq(Property(name = "Geographic Location (GAZ)",
          value = geoLocNameGaz.getOrElse("NotFound"),
          valueReference = Some(ValueReference(
            codeValue = geoLocNameGazEnvo.map{_.value}.getOrElse("NotFound"),
            url = geoLocNameGazEnvo.map{_.url}.getOrElse("NotFound")
          )))
        ),
        Seq({
          val ov = envFeature.map{_.value.split('(').head.stripLineEnd}
          Property(name = "Environment Feature",
          value = ov.getOrElse("NotFound"),
          valueReference = Some(ValueReference(
            name = ov,
            codeValue = envFeature.flatMap{_.value.split('(').tail.headOption}.flatMap{_.split(')').headOption}.getOrElse("NotFound"),
            url = envFeature.map{_.url}.getOrElse("NotFound")
          ))
        )}),
        Seq({
          val ov = envMaterial.map{_.value.split('(').head.stripLineEnd}
          Property(name = "Environment Material",
          value = ov.getOrElse("NotFound"),
          valueReference = Some(ValueReference(
            name = ov,
            codeValue = envMaterial.flatMap{_.value.split('(').tail.headOption}.flatMap{_.split(')').headOption}.getOrElse("NotFound"),
            url = envFeature.map{_.url}.getOrElse("NotFound")
          ))
        )}),
        Seq(Property(name = "Organism",
          value = organism.getOrElse("NotFound"),
          valueReference = Some(ValueReference(
            name = Some("NCBI Taxon Identifier"),
            codeValue = ncbiTaxonIdentifier.map{i => s"NCBITaxon:$i"}.getOrElse("NotFound"),
            url = ncbiTaxonIdentifier.map{_.url}.getOrElse("NotFound")
          ))
        )),
        Seq(Property(name = "Full Scientific Name",
          value = fullScientificName.getOrElse("NotFound"),
          valueReference = Some(ValueReference(
            name = fullScientificName,
            codeValue = ncbiTaxonIdentifier.map{i => s"NCBITaxon:$i"}.getOrElse("NotFound"),
            url = ncbiTaxonIdentifier.map{_.url}.getOrElse("NotFound")
          ))
        ))
      ).flatten
    )
  }
  def toJsonld(): String = Json.serialize(toBioschema())
}

case class RecordsModel(databaseType: String,
                        record: Seq[SampleModel])

case class MarrefModel(records: RecordsModel)
