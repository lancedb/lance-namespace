from pathlib import Path


SPEC_PATH = Path(__file__).resolve().parents[1] / "docs" / "src" / "spec.yaml"
FIELD_PATH_REF = '$ref: "#/components/schemas/FieldPath"'


def _spec_text() -> str:
    return SPEC_PATH.read_text()


def _block_after(text: str, marker: str) -> str:
    start = text.index(marker)
    lines = text[start:].splitlines()
    base_indent = len(lines[0]) - len(lines[0].lstrip())
    block = [lines[0]]
    for line in lines[1:]:
        if line.strip() and len(line) - len(line.lstrip()) <= base_indent:
            break
        block.append(line)
    return "\n".join(block)


def test_field_path_schema_documents_nested_path_contract():
    block = _block_after(_spec_text(), "    FieldPath:")

    assert "type: string" in block
    assert "minLength: 1" in block
    assert "addressed by joining path segments with `.`" in block
    assert "literal `.`" in block
    assert "Backticks inside a quoted segment are escaped" in block
    assert "same leaf name under different parents unambiguous" in block
    assert "canonical display form" in block


def test_projection_index_and_metadata_fields_use_field_path_schema():
    text = _spec_text()

    for marker in [
        "    QueryTableRequest:",
        "    AnalyzeTableQueryPlanRequest:",
        "    CreateTableIndexRequest:",
        "    IndexContent:",
        "    StringFtsQuery:",
        "    MatchQuery:",
        "    PhraseQuery:",
        "    MergeInsertIntoTableRequest:",
        "    AddVirtualColumnEntry:",
        "    UpdateFieldMetadataEntry:",
        "    AlterColumnsEntry:",
        "    AlterVirtualColumnEntry:",
        "    AlterTableBackfillColumnsRequest:",
        "    MaterializedViewUdtfEntry:",
        "    AlterTableDropColumnsRequest:",
    ]:
        assert FIELD_PATH_REF in _block_after(text, marker), marker


def test_rest_merge_insert_on_parameter_uses_field_path_schema():
    block = _block_after(_spec_text(), '      - name: "on"')

    assert "description: Field path to use for matching rows" in block
    assert FIELD_PATH_REF in block


def test_sql_expression_fields_reference_field_path_contract():
    text = _spec_text()

    for marker in [
        "    CountTableRowsRequest:",
        "    MergeInsertIntoTableRequest:",
        "    UpdateTableRequest:",
        "    DeleteFromTableRequest:",
        "    QueryTableRequest:",
        "    AnalyzeTableQueryPlanRequest:",
    ]:
        assert "field path syntax" in _block_after(text, marker), marker
