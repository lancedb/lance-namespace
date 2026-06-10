from pathlib import Path


SPEC_PATH = Path(__file__).resolve().parents[1] / "docs" / "src" / "spec.yaml"


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


def _assert_field_path_contract(block: str) -> None:
    assert "type: string" in block
    assert "minLength: 1" in block
    assert "dot-separated segments" in block
    assert "literal dots" in block
    assert "backtick" in block


def test_field_path_contract_is_inline_string_schema():
    text = _spec_text()

    assert "    FieldPath:" not in text
    assert "#/components/schemas/FieldPath" not in text


def test_projection_index_and_metadata_fields_document_field_path_contract():
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
        _assert_field_path_contract(_block_after(text, marker))


def test_ambiguous_and_unresolved_paths_define_display_and_errors():
    text = _spec_text()

    for marker in [
        "    CreateTableIndexRequest:",
        "    MergeInsertIntoTableRequest:",
        "    UpdateFieldMetadataEntry:",
        "    AlterColumnsEntry:",
        "    AlterTableBackfillColumnsRequest:",
        "    AlterTableDropColumnsRequest:",
    ]:
        block = _block_after(text, marker)
        assert "canonical full paths" in block
        assert "leaf names alone only identify top-level fields" in block
        assert "InvalidInput or TableColumnNotFound" in block


def test_rest_merge_insert_on_parameter_documents_field_path_contract():
    block = _block_after(_spec_text(), '      - name: "on"')

    _assert_field_path_contract(block)
    assert "canonical full paths" in block
    assert "InvalidInput or TableColumnNotFound" in block


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
        block = _block_after(text, marker)
        assert "field path syntax" in block, marker
        assert "literal dots require backtick-quoted segments" in block, marker
